package com.hch.ioc.core.workers.impl;

import com.hch.ioc.core.definitions.IocInjectDefinition;
import com.hch.ioc.core.definitions.IocScanDefinition;
import com.hch.ioc.core.exceptions.SimpleIocException;
import com.hch.ioc.core.registries.BeanDefinitionRegistry;
import com.hch.ioc.core.utils.Utils;
import com.hch.ioc.core.workers.Worker;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Stack;

public class HierarchyBeanDefinitionsWorker implements Worker {

    private static HierarchyBeanDefinitionsWorker hierarchyBeanDefinitionsWorker;

    private HierarchyBeanDefinitionsWorker() {
    }

    public static HierarchyBeanDefinitionsWorker getInstance() {
        if (hierarchyBeanDefinitionsWorker == null) {
            hierarchyBeanDefinitionsWorker = new HierarchyBeanDefinitionsWorker();
        }
        return hierarchyBeanDefinitionsWorker;
    }

    @Override
    public void start() {
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
        return Utils.findIocScanDefinitionByType(name);
    }
}
