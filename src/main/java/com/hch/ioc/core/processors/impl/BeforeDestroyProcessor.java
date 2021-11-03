package com.hch.ioc.core.processors.impl;

import com.hch.ioc.core.processors.Processor;
import com.hch.ioc.core.processors.context.BeanProcessContext;
import com.hch.ioc.core.registries.BeanRegistry;

public class BeforeDestroyProcessor implements Processor {

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
