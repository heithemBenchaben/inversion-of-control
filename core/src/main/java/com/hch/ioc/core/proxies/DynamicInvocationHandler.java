package com.hch.ioc.core.proxies;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class DynamicInvocationHandler implements InvocationHandler {

    private Object target;
    private ProxyProvider proxyProvider;

    public DynamicInvocationHandler(Object target, ProxyProvider proxyProvider) {
        this.target = target;
        this.proxyProvider = proxyProvider;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        Object result = null;
        try {
            proxyProvider.before(proxy, method, args);
            result = method.invoke(target, args);
            proxyProvider.after(proxy, method, args);
        } catch (Exception e) {
            proxyProvider.afterThrow(proxy, method, args, e);
        } finally {
            proxyProvider.doFinally(proxy, method, args);
        }
        return result;
    }
}
