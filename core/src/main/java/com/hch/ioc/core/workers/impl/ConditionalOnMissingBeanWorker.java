package com.hch.ioc.core.workers.impl;

import com.hch.ioc.core.definitions.IocScanDefinition;
import com.hch.ioc.core.registries.BeanDefinitionRegistry;
import com.hch.ioc.core.workers.Worker;

public class ConditionalOnMissingBeanWorker implements Worker {

    private static ConditionalOnMissingBeanWorker conditionalOnMissingBeanProcessor;

    private ConditionalOnMissingBeanWorker() {
    }

    public static ConditionalOnMissingBeanWorker getInstance() {
        if (conditionalOnMissingBeanProcessor == null) {
            conditionalOnMissingBeanProcessor = new ConditionalOnMissingBeanWorker();
        }
        return conditionalOnMissingBeanProcessor;
    }

    @Override
    public void start() {
        // put missing bean to the registry if not exist
        BeanDefinitionRegistry
                .getInstance()
                .getConditionalOnMissingBeanRegistry()
                .forEach((key, value) -> {
                    if (!checkConditionalOnMissingBean(value)) {
                        BeanDefinitionRegistry
                                .getInstance()
                                .getRegistry()
                                .put(key, value);
                    }
                });

    }

    private boolean checkConditionalOnMissingBean(IocScanDefinition iocScanDefinition) {
        return BeanDefinitionRegistry
                .getInstance()
                .getRegistry()
                .values()
                .stream()
                .anyMatch(iocScanDef ->
                        iocScanDef
                                .getTypes()
                                .stream()
                                .anyMatch(t -> iocScanDefinition.getTypes().contains(t))
                );
    }
}
