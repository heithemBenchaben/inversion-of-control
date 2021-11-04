package com.hch.ioc.core.workers.impl;

import com.hch.ioc.core.annotations.ConditionalOn;
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
    public static final String DEFAULT = "default";

    /**
     * build iocScanDefinition for every class annotated by IocScan and fill the BeanDefinitionRegistry
     */
    @Override
    public void start() {
        // build iocScanDefinitionMap
        buildIocScanDefinitionMap();
        // cleanUp mismatch conditionalOn
        cleanUpRegistryByConditionalOn();
        // add missing bean @ConditionalOnMissingBean
        checkConditionalOnMissingBean();
        // clean registry by removing mismatch profiles
        cleanUpRegistryByProfile();
        // organize the registry with correct hierarchy
        // child on top of parents
        toHierarchyRegistry();
    }

    private void cleanUpRegistryByConditionalOn() {
        Map<String, IocScanDefinition> cleanUpRegistryByConditionalOnResult = new HashMap<>();
        // loop over all conditionalOnMissingBean definitions under the BeanDefinitionRegistry
        BeanDefinitionRegistry
                .getInstance()
                .getRegistry()
                .forEach((key, value) -> {
                    if (value.getConditionalOnDefinition() == null || (value.getConditionalOnDefinition() != null && ((ConditionalOn) value.getConditionalOnDefinition().getConditionalOn()).check())) {
                        cleanUpRegistryByConditionalOnResult.put(key, value);
                    }
                });
        BeanDefinitionRegistry.getInstance().setRegistry(cleanUpRegistryByConditionalOnResult);
    }

    private void checkConditionalOnMissingBean() {
        // loop over all conditionalOnMissingBean definitions under the BeanDefinitionRegistry
        BeanDefinitionRegistry
                .getInstance()
                .getConditionalOnMissingBeanRegistry()
                .values()
                .forEach(missingBean -> {
                    // put missing bean to the registry if not exist
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
        // initialize clean up map
        Map<String, IocScanDefinition> cleanUpRegistryResult = new HashMap<>();
        // retrieve configured profiles from properties
        String configuredProfiles = PropertiesRegistry
                .getInstance()
                .getProperties()
                .get(ACTIVE_PROFILES);
        // if configuredProfiles is not configured so active profile is set to default
        // if configuredProfiles is configured so accept this profile and the default one
        String activeProfiles = (configuredProfiles == null ? DEFAULT : configuredProfiles.concat(DEFAULT));

        // filter registry to accept only match profiles
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
        // set the clean up map to the registry
        BeanDefinitionRegistry.getInstance().setRegistry(cleanUpRegistryResult);
    }

    // check if iocScanDefinition match the active profiles
    private boolean checkMatchingProfile(IocScanDefinition iocScanDefinition, String activeProfiles) {
        return iocScanDefinition
                .getProfiles()
                .stream()
                .anyMatch(profile ->
                        activeProfiles.contains(profile)
                );
    }

    private void buildIocScanDefinitionMap() {
        // loop over all classes annotated by IocScan
        IocScanClazzRegistry
                .getInstance()
                .getIocScanClazzList()
                .stream()
                .forEach(clazz -> {
                    // build iocScanDefinition for clazz
                    IocScanDefinition iocScanDefinition = doScans(clazz);
                    // put the iocScanDefinition into the correct map in BeanDefinitionRegistry
                    if ((iocScanDefinition.getConditionalOnMissingBeanDefinition() == null)) {
                        // put under registry
                        BeanDefinitionRegistry.getInstance().getRegistry().put(iocScanDefinition.getClazz().getName(), iocScanDefinition);
                    } else {
                        // put under conditionalOnMissingBeanRegistry
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
        // Initialize registry
        // linkedHashMap to conserve the order of put
        Map<String, IocScanDefinition> registry = new LinkedHashMap<>();
        // build a stack containing parents on bottom of child
        Stack<IocScanDefinition> stack = buildStack();
        // fill the registry by pop the stack
        while (!stack.empty()) {
            IocScanDefinition iocScanDefinition = stack.pop();
            registry.put(iocScanDefinition.getClazz().getName(), iocScanDefinition);
        }
        BeanDefinitionRegistry.getInstance().setRegistry(registry);
    }

    /**
     * @return
     */
    private Stack<IocScanDefinition> buildStack() {
        // Initialize a stack
        Stack<IocScanDefinition> stack = new Stack<>();
        // loop over registry
        BeanDefinitionRegistry
                .getInstance()
                .getRegistry()
                .values()
                .forEach(iocScanDefinition ->
                        // fill the
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
                        IocScanDefinition iocScanDefinitionTarget = getTarget(iocInjectDefinition);
                        if (filledIocScanDefinitionMap.get(iocInjectDefinition.getField().getType().getName()) == null) {
                            // recursive call
                            fillStack(iocScanDefinitionTarget, stack, filledIocScanDefinitionMap);
                        } else {
                            throw new SimpleIocException(String.format("Circular dependency injection between *** %s *** and === %s ===", iocScanDefinition.getClazz().getName(), iocScanDefinitionTarget.getClazz().getName()));
                        }
                    });
        }
    }

    private IocScanDefinition getTarget(IocInjectDefinition iocInjectDefinition) {
        String name = iocInjectDefinition.getField().getType().getName();
        return ContainerUtils.findIocScanDefinitionByType(name);
    }
}