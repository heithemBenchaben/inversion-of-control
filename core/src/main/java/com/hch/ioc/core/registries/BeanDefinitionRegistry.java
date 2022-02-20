package com.hch.ioc.core.registries;

import com.hch.ioc.core.definitions.IocScanDefinition;

import java.util.LinkedHashMap;
import java.util.Map;

public class BeanDefinitionRegistry {

    private static BeanDefinitionRegistry beanDefinitionRegistry;

    private Map<String, IocScanDefinition> registry;

    private Map<String, IocScanDefinition> conditionalOnMissingBeanRegistry;

    private BeanDefinitionRegistry() {
        this.registry = new LinkedHashMap<>();
        this.conditionalOnMissingBeanRegistry = new LinkedHashMap<>();
    }

    public static BeanDefinitionRegistry getInstance() {
        if (beanDefinitionRegistry == null) {
            beanDefinitionRegistry = new BeanDefinitionRegistry();
        }
        return beanDefinitionRegistry;
    }

    public Map<String, IocScanDefinition> getRegistry() {
        return registry;
    }

    public void setRegistry(Map<String, IocScanDefinition> registry) {
        this.registry = registry;
    }

    public Map<String, IocScanDefinition> getConditionalOnMissingBeanRegistry() {
        return conditionalOnMissingBeanRegistry;
    }
}
