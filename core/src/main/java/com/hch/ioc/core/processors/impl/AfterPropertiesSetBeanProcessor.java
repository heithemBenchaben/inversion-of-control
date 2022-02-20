package com.hch.ioc.core.processors.impl;

import com.hch.ioc.core.definitions.AfterPropertiesSetDefinition;
import com.hch.ioc.core.exceptions.SimpleIocException;
import com.hch.ioc.core.processors.BeanProcessor;
import com.hch.ioc.core.processors.context.BeanProcessContext;

import java.lang.reflect.InvocationTargetException;

public class AfterPropertiesSetBeanProcessor implements BeanProcessor {

    private static AfterPropertiesSetBeanProcessor afterPropertiesSetBeanProcessor;

    private AfterPropertiesSetBeanProcessor() {
    }

    public static AfterPropertiesSetBeanProcessor getInstance() {
        if (afterPropertiesSetBeanProcessor == null) {
            afterPropertiesSetBeanProcessor = new AfterPropertiesSetBeanProcessor();
        }
        return afterPropertiesSetBeanProcessor;
    }

    /**
     * execute method annotated by @AfterPropertiesSet if exist
     *
     * @param beanProcessContext
     */
    @Override
    public void process(BeanProcessContext beanProcessContext) {
        afterPropertiesSet(beanProcessContext.getIocScanDefinition().getAfterPropertiesSetDefinition(), beanProcessContext.getObject());
    }

    private void afterPropertiesSet(AfterPropertiesSetDefinition afterPropertiesSetDefinition, Object object) {
        try {
            if (afterPropertiesSetDefinition != null) {
                afterPropertiesSetDefinition.getMethod().invoke(object);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new SimpleIocException(e);
        }
    }
}
