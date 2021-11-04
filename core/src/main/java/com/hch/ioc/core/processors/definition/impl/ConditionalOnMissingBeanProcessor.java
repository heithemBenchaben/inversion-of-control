package com.hch.ioc.core.processors.definition.impl;

import com.hch.ioc.core.definitions.IocScanDefinition;
import com.hch.ioc.core.processors.definition.BeanDefinitionProcessor;
import com.hch.ioc.core.registries.BeanDefinitionRegistry;

public class ConditionalOnMissingBeanProcessor implements BeanDefinitionProcessor {
    @Override
    public void process() {
        processConditionalOnMissingBean();
    }

    private void processConditionalOnMissingBean() {
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
}
