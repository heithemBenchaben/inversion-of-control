package com.hch.ioc.core.processors.impl;

import com.hch.ioc.core.processors.BeanProcessor;
import com.hch.ioc.core.processors.context.BeanProcessContext;
import com.hch.ioc.core.registries.BeanRegistry;

public class BeforeDestroyBeanProcessor implements BeanProcessor {

    private static BeforeDestroyBeanProcessor beforeDestroyBeanProcessor;

    private BeforeDestroyBeanProcessor() {
    }

    public static BeforeDestroyBeanProcessor getInstance() {
        if (beforeDestroyBeanProcessor == null) {
            beforeDestroyBeanProcessor = new BeforeDestroyBeanProcessor();
        }
        return beforeDestroyBeanProcessor;
    }

    /**
     * Add the created instance to the before destroy beans if there is a method annotated by @BeforeDestroy
     *
     * @param beanProcessContext
     */
    @Override
    public void process(BeanProcessContext beanProcessContext) {
        if (beanProcessContext.getIocScanDefinition().getBeforeDestroyDefinition() != null) {
            BeanRegistry.getInstance().getBeforeDestroyBeans().add(beanProcessContext);
        }
    }
}
