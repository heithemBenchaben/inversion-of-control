package com.hch.ioc.core.processors.impl;

import com.hch.ioc.core.enums.Scope;
import com.hch.ioc.core.processors.BeanProcessor;
import com.hch.ioc.core.processors.context.BeanProcessContext;
import com.hch.ioc.core.registries.BeanRegistry;

public class ReadyToUseBeanBeanProcessor implements BeanProcessor {

    private static ReadyToUseBeanBeanProcessor readyToUseBeanBeanProcessor;

    private ReadyToUseBeanBeanProcessor() {
    }

    public static ReadyToUseBeanBeanProcessor getInstance() {
        if (readyToUseBeanBeanProcessor == null) {
            readyToUseBeanBeanProcessor = new ReadyToUseBeanBeanProcessor();
        }
        return readyToUseBeanBeanProcessor;
    }

    /**
     * Add the created instance to the registry if the scope is singleton
     * for prototype the instance will be created at runtime when invoking BeanRegistry.getBean(..)
     *
     * @param beanProcessContext
     */
    @Override
    public void process(BeanProcessContext beanProcessContext) {
        if (beanProcessContext.getIocScanDefinition().getScope() == Scope.SINGLETON)
            beanProcessContext
                    .getIocScanDefinition()
                    .getTypes()
                    .forEach(type ->
                            BeanRegistry.getInstance().getBeans().put(type.getName(), beanProcessContext.getObject())
                    );
    }
}
