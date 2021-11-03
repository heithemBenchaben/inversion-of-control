package com.hch.ioc.core.processors.impl;

import com.hch.ioc.core.definitions.IocScanDefinition;
import com.hch.ioc.core.exceptions.SimpleIocException;
import com.hch.ioc.core.processors.Processor;
import com.hch.ioc.core.processors.context.BeanProcessContext;

import java.lang.reflect.InvocationTargetException;

public class BeanInitializerProcessor implements Processor {

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
