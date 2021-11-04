package com.hch.ioc.core.processors.bean.impl;

import com.hch.ioc.core.definitions.AfterPropertiesSetDefinition;
import com.hch.ioc.core.exceptions.SimpleIocException;
import com.hch.ioc.core.processors.bean.BeanProcessor;
import com.hch.ioc.core.processors.bean.context.BeanProcessContext;

import java.lang.reflect.InvocationTargetException;

public class AfterPropertiesSetBeanProcessor implements BeanProcessor {

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
            e.printStackTrace();
            throw new SimpleIocException(e);
        }
    }
}
