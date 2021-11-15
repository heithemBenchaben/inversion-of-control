package com.hch.ioc.core.processors.bean.impl;

import com.hch.ioc.core.exceptions.SimpleIocException;
import com.hch.ioc.core.processors.bean.BeanProcessor;
import com.hch.ioc.core.processors.bean.context.BeanProcessContext;
import com.hch.ioc.core.proxies.DynamicInvocationHandler;

import java.lang.reflect.Proxy;

public class ProxyBeanProcessor implements BeanProcessor {

    @Override
    public void process(BeanProcessContext beanProcessContext) {
        if (beanProcessContext.getIocScanDefinition().getCacheableDefinition() != null) {
            try {
                Object proxy = Proxy.newProxyInstance(
                        beanProcessContext.getIocScanDefinition().getTypes().get(1).getClassLoader(),
                        new Class[]{Class.forName(beanProcessContext.getIocScanDefinition().getTypes().get(1).getName())},
                        new DynamicInvocationHandler(beanProcessContext.getObject())
                );
                beanProcessContext.setObject(proxy);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                throw new SimpleIocException(e);
            }
        }
    }
}
