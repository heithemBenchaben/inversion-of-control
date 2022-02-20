package com.hch.ioc.core.processors.impl;

import com.hch.ioc.core.definitions.IocScanDefinition;
import com.hch.ioc.core.exceptions.SimpleIocException;
import com.hch.ioc.core.processors.BeanProcessor;
import com.hch.ioc.core.processors.context.BeanProcessContext;

import java.lang.reflect.InvocationTargetException;

public class BeanInitializerBeanProcessor implements BeanProcessor {

    private static BeanInitializerBeanProcessor beanInitializerBeanProcessor;

    private BeanInitializerBeanProcessor() {
    }

    public static BeanInitializerBeanProcessor getInstance() {
        if (beanInitializerBeanProcessor == null) {
            beanInitializerBeanProcessor = new BeanInitializerBeanProcessor();
        }
        return beanInitializerBeanProcessor;
    }

    /**
     * create an empty object by invoking the empty constructor
     *
     * @param beanProcessContext
     */
    @Override
    public void process(BeanProcessContext beanProcessContext) {
        beanProcessContext
                .setObject(
                        initialize(
                                beanProcessContext.getIocScanDefinition()
                        )
                );
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
            throw new SimpleIocException(e);
        }
    }
}
