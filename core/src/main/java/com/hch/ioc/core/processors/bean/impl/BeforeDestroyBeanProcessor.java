package com.hch.ioc.core.processors.bean.impl;

import com.hch.ioc.core.processors.bean.BeanProcessor;
import com.hch.ioc.core.processors.bean.context.BeanProcessContext;
import com.hch.ioc.core.registries.BeanRegistry;

public class BeforeDestroyBeanProcessor implements BeanProcessor {

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