package com.hch.ioc.core.processors.definition.impl;

import com.hch.ioc.core.definitions.IocScanDefinition;
import com.hch.ioc.core.processors.definition.BeanDefinitionProcessor;
import com.hch.ioc.core.registries.BeanDefinitionRegistry;
import com.hch.ioc.core.registries.PropertiesRegistry;

import java.util.HashMap;
import java.util.Map;

public class ConditionalOnCleanUpProcessor implements BeanDefinitionProcessor {

    private static ConditionalOnCleanUpProcessor conditionalOnCleanUpProcessor;

    private ConditionalOnCleanUpProcessor() {
    }

    public static ConditionalOnCleanUpProcessor getInstance() {
        if (conditionalOnCleanUpProcessor == null) {
            conditionalOnCleanUpProcessor = new ConditionalOnCleanUpProcessor();
        }
        return conditionalOnCleanUpProcessor;
    }

    @Override
    public void process() {
        processConditionalOn();
    }

    private void processConditionalOn() {
        Map<String, IocScanDefinition> cleanUpRegistryByConditionalOnResult = new HashMap<>();
        // loop over all conditionalOnMissingBean definitions under the BeanDefinitionRegistry
        BeanDefinitionRegistry
                .getInstance()
                .getRegistry()
                .forEach((key, value) -> {
                    if (value.getConditionalOnDefinition() == null || (value.getConditionalOnDefinition() != null && checkPropertyForConditionalOn(value))) {
                        cleanUpRegistryByConditionalOnResult.put(key, value);
                    }
                });
        BeanDefinitionRegistry.getInstance().setRegistry(cleanUpRegistryByConditionalOnResult);
    }

    private boolean checkPropertyForConditionalOn(IocScanDefinition iocScanDefinition) {
        String property = iocScanDefinition.getConditionalOnDefinition().getConditionalOn().property();
        String having = iocScanDefinition.getConditionalOnDefinition().getConditionalOn().having();
        return PropertiesRegistry.getInstance().getProperties().get(property).equals(having);
    }
}
