package com.hch.ioc.core.processors.impl;

import com.hch.ioc.core.definitions.IocScanDefinition;
import com.hch.ioc.core.enums.ProxyType;
import com.hch.ioc.core.exceptions.SimpleIocException;
import com.hch.ioc.core.processors.BeanProcessor;
import com.hch.ioc.core.processors.context.BeanProcessContext;
import com.hch.ioc.core.proxies.LoggerProxyProvider;
import com.hch.ioc.core.proxies.DynamicInvocationHandler;
import com.hch.ioc.core.proxies.ProxyProvider;
import com.hch.ioc.core.registries.BeanRegistry;

import java.lang.reflect.Proxy;

public class ProxyBeanProcessor implements BeanProcessor {

    private static ProxyBeanProcessor proxyBeanProcessor;

    private ProxyBeanProcessor() {
    }

    public static ProxyBeanProcessor getInstance() {
        if (proxyBeanProcessor == null) {
            proxyBeanProcessor = new ProxyBeanProcessor();
        }
        return proxyBeanProcessor;
    }

    @Override
    public void process(BeanProcessContext beanProcessContext) {
        if (beanProcessContext.getIocScanDefinition().getProxied()) {
            try {
                Object proxy = Proxy.newProxyInstance(
                        beanProcessContext.getIocScanDefinition().getTypes().get(1).getClassLoader(),
                        new Class[]{Class.forName(beanProcessContext.getIocScanDefinition().getTypes().get(1).getName())},
                        new DynamicInvocationHandler(beanProcessContext.getObject(), defineProxyProvider(beanProcessContext.getIocScanDefinition()))
                );
                beanProcessContext.setObject(proxy);
            } catch (ClassNotFoundException e) {
                throw new SimpleIocException(e);
            }
        }
    }

    private ProxyProvider defineProxyProvider(IocScanDefinition iocScanDefinition) {
        if (iocScanDefinition.getProxyDefinition().getProxy().type() == ProxyType.LOGGER)
            return (ProxyProvider) BeanRegistry.getInstance().getBean(LoggerProxyProvider.class);
        else throw new SimpleIocException("no proxy provider found");
    }
}
