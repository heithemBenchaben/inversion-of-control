package com.hch.ioc.core.workers.impl;

import com.hch.ioc.core.definitions.IocInjectDefinition;
import com.hch.ioc.core.definitions.IocScanDefinition;
import com.hch.ioc.core.exceptions.SimpleIocException;
import com.hch.ioc.core.registries.BeanDefinitionRegistry;
import com.hch.ioc.core.registries.IocScanClazzRegistry;
import com.hch.ioc.core.registries.PropertiesRegistry;
import com.hch.ioc.core.scanners.template.ScannerTemplateProvider;
import com.hch.ioc.core.utils.ContainerUtils;
import com.hch.ioc.core.workers.Worker;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Stack;

public class BeanDefinitionWorker implements Worker {

    public static final String ACTIVE_PROFILES = "active.profiles";

    /**
     * build iocScanDefinition for every class annotated by IocScan and fill the BeanDefinitionRegistry
     */
    @Override
    public void start() {
        // build iocScanDefinitionMap
        buildIocScanDefinitionMap();
        // add missing bean @ConditionalOnMissingBean
        addConditionalOnMissingBean();
        // organize iocScanDefinitionMap with correct hierarchy
        toHierarchyRegistry();
        // clean hierarchyRegistry by removing mismatch profiles
        cleanUpRegistryByProfile();
    }

    private void addConditionalOnMissingBean() {
        BeanDefinitionRegistry
                .getInstance()
                .getConditionalOnMissingBeanRegistry()
                .values()
                .forEach(missingBean -> {
                    if (!checkConditionalOnMissingBean(missingBean)) {
                        BeanDefinitionRegistry
                                .getInstance()
                                .getRegistry()
                                .put(missingBean.getClazz().getName(), missingBean);
                    }
                });
    }

    private boolean checkConditionalOnMissingBean(IocScanDefinition missingBean) {
        return BeanDefinitionRegistry
                .getInstance()
                .getRegistry()
                .values()
                .stream()
                .anyMatch(iocScanDefinition ->
                        iocScanDefinition
                                .getTypes()
                                .stream()
                                .anyMatch(t -> missingBean.getTypes().contains(t))
                );
    }

    private void cleanUpRegistryByProfile() {
        Map<String, IocScanDefinition> cleanUpRegistryResult = new LinkedHashMap<>();
        // retrieve configured profiles
        String configuredProfiles = PropertiesRegistry
                .getInstance()
                .getProperties()
                .get(ACTIVE_PROFILES);
        // if configuredProfiles is not configured so active profile is set to default
        String activeProfiles = (configuredProfiles == null ? "default" : configuredProfiles.concat("default"));

        // filter hierarchyRegistry to accept only match profile
        BeanDefinitionRegistry
                .getInstance()
                .getRegistry()
                .entrySet()
                .stream()
                .forEach(keyValue -> {
                            if (checkMatchingProfile(keyValue.getValue(), activeProfiles)) {
                                cleanUpRegistryResult.put(keyValue.getKey(), keyValue.getValue());
                            }
                        }
                );
        BeanDefinitionRegistry.getInstance().setRegistry(cleanUpRegistryResult);
    }

    // check if iocScanDefinition match the active profiles
    private boolean checkMatchingProfile(IocScanDefinition iocScanDefinition, String activeProfiles) {
        return iocScanDefinition
                .getProfiles()
                .stream()
                .filter(profile ->
                        activeProfiles.contains(profile)
                )
                .findFirst()
                .isPresent();
    }

    private void buildIocScanDefinitionMap() {
        // loop over all classes annotated by IocScan
        IocScanClazzRegistry
                .getInstance()
                .getIocScanClazzList()
                .stream()
                .forEach(clazz -> {
                    IocScanDefinition iocScanDefinition = doScans(clazz);
                    if ((iocScanDefinition.getConditionalOnMissingBeanDefinition() == null)) {
                        BeanDefinitionRegistry.getInstance().getRegistry().put(iocScanDefinition.getClazz().getName(), iocScanDefinition);
                    } else {
                        BeanDefinitionRegistry.getInstance().getConditionalOnMissingBeanRegistry().put(iocScanDefinition.getClazz().getName(), iocScanDefinition);
                    }
                });
    }

    /**
     * build IocScanDefinition for clazz based on list of Scanner
     *
     * @param clazz
     * @return
     */
    private static IocScanDefinition doScans(Class<?> clazz) {
        // instantiate the iocScanDefinition for the target clazz
        IocScanDefinition iocScanDefinition = new IocScanDefinition(clazz);
        // loop over all scanners and update the iocScanDefinition
        ScannerTemplateProvider.doScans(iocScanDefinition);
        // return the iocScanDefinition as result after applying all scanner
        return iocScanDefinition;
    }

    /**
     * organize iocScanDefinitionMap
     * children on top of parent dependency
     *
     * @return
     */
    private void toHierarchyRegistry() {
        Map<String, IocScanDefinition> map = new LinkedHashMap<>();
        Stack<IocScanDefinition> stack = buildStack();
        while (!stack.empty()) {
            IocScanDefinition iocScanDefinition = stack.pop();
            map.put(iocScanDefinition.getClazz().getName(), iocScanDefinition);
        }
        BeanDefinitionRegistry.getInstance().setRegistry(map);
    }

    /**
     * @return
     */
    private Stack<IocScanDefinition> buildStack() {
        Stack<IocScanDefinition> stack = new Stack<>();
        Map<String, IocScanDefinition> filledIocScanDefinitionMap = new HashMap<>();
        // loop over iocScanDefinitionMap
        BeanDefinitionRegistry
                .getInstance()
                .getRegistry()
                .values()
                .forEach(iocScanDefinition ->
                        fillStack(iocScanDefinition, stack, new HashMap<>())
                );
        return stack;
    }

    private void fillStack(IocScanDefinition iocScanDefinition, Stack<IocScanDefinition> stack, Map<String, IocScanDefinition> filledIocScanDefinitionMap) {
        stack.push(iocScanDefinition);
        filledIocScanDefinitionMap.put(iocScanDefinition.getClazz().getName(), iocScanDefinition);
        if (iocScanDefinition.getIocInjectDefinitions() != null || !iocScanDefinition.getIocInjectDefinitions().isEmpty()) {
            iocScanDefinition
                    .getIocInjectDefinitions()
                    .forEach(iocInjectDefinition -> {
                        IocScanDefinition iocsd = getTarget(iocInjectDefinition, BeanDefinitionRegistry.getInstance().getRegistry());
                        if (filledIocScanDefinitionMap.get(iocInjectDefinition.getField().getType().getName()) == null) {
                            // recursive call
                            fillStack(iocsd, stack, filledIocScanDefinitionMap);
                        } else {
                            throw new SimpleIocException(String.format("Circular dependency injection between *** %s *** and === %s ===", iocScanDefinition.getClazz().getName(), iocsd.getClazz().getName()));
                        }
                    });
        }
    }

    private IocScanDefinition getTarget(IocInjectDefinition iocInjectDefinition, Map<String, IocScanDefinition> iocScanDefinitionMap) {
        String name = iocInjectDefinition.getField().getType().getName();
        return ContainerUtils.findIocScanDefinitionByType(name, iocScanDefinitionMap);
    }
}