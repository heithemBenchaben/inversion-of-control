package com.hch.ioc.core.workers.impl;

import com.hch.ioc.core.processors.context.BeanProcessContext;
import com.hch.ioc.core.registries.BeanRegistry;
import com.hch.ioc.core.workers.Worker;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ShutdownHookWorker implements Worker {

    private static ShutdownHookWorker shutdownHookWorker;

    private ShutdownHookWorker() {
    }

    public static ShutdownHookWorker getInstance() {
        if (shutdownHookWorker == null) {
            shutdownHookWorker = new ShutdownHookWorker();
        }
        return shutdownHookWorker;
    }

    @Override
    public void start() {
        // add shutdown hook worker in order to invoke all before destroy method
        Runtime.getRuntime().addShutdownHook(
                new Thread("app-shutdown-hook") {
                    @Override
                    public void run() {
                        BeanRegistry.getInstance().getBeforeDestroyBeans()
                                .forEach(beanProcessContext ->
                                        invokeBeforeDestroy(beanProcessContext)
                                );
                    }
                });
    }

    private static void invokeBeforeDestroy(BeanProcessContext beanProcessContext) {
        try {
            getBeforeDestroyMethod(beanProcessContext).invoke(beanProcessContext.getObject());
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private static Method getBeforeDestroyMethod(BeanProcessContext beanProcessContext) {
        return beanProcessContext.getIocScanDefinition().getBeforeDestroyDefinition().getMethod();
    }
}
