package com.hch.ioc.core.proxies;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class DynamicInvocationHandler implements InvocationHandler {

    private Object target;

    public DynamicInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        System.out.println("before invoke " + method.getName());
        Object result = method.invoke(target, args);
        System.out.println("after invoke " + method.getName());
        return result;
    }
}
