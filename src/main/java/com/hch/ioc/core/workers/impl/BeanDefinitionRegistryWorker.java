package com.hch.ioc.core.workers.impl;

import com.hch.ioc.core.processors.definition.template.BeanDefinitionProcessorsTemplate;
import com.hch.ioc.core.workers.Worker;

public class BeanDefinitionRegistryWorker implements Worker {
    @Override
    public void start() {
        BeanDefinitionProcessorsTemplate.process();
    }
}
