package com.hch.ioc.core.processors.bean.impl;

import com.hch.ioc.core.definitions.IocScanDefinition;
import com.hch.ioc.core.exceptions.SimpleIocException;
import com.hch.ioc.core.processors.bean.BeanProcessor;
import com.hch.ioc.core.processors.bean.context.BeanProcessContext;

import java.lang.reflect.InvocationTargetException;

public class BeanInitializerBeanProcessor implements BeanProcessor {

    /**
     * create an empty object by invoking the empty constructor
     * @param beanProcessContext
     */
    @Override
    public void process(BeanProcessContext beanProcessContext) {
        Object object = initialize(beanProcessContext.getIocScanDefinition());
        beanProcessContext.setObject(object);
    }

    /**
     * Create an instance based on empty constructor
     *
     * @param iocScanDefinition
     * @return
     */
    private Object initialize(IocScanDefinition iocScanDefinition) {
        try {
            return (iocScanDefinition.getClazz().getConstructors()[0]).newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new SimpleIocException(e);
        }
    }
}
