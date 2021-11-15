package com.hch.ioc.core.workers.impl;

import com.hch.ioc.core.processors.definition.template.BeanDefinitionProcessorsTemplate;
import com.hch.ioc.core.workers.Worker;

public class BeanDefinitionRegistryWorker implements Worker {

    private static BeanDefinitionRegistryWorker beanDefinitionRegistryWorker;

    private BeanDefinitionRegistryWorker() {
    }

    public static BeanDefinitionRegistryWorker getInstance() {
        if (beanDefinitionRegistryWorker == null) {
            beanDefinitionRegistryWorker = new BeanDefinitionRegistryWorker();
        }
        return beanDefinitionRegistryWorker;
    }

    @Override
    public void start() {
        BeanDefinitionProcessorsTemplate.process();
    }
}
