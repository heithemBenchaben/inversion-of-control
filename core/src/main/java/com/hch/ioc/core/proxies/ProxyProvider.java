package com.hch.ioc.core.proxies;

import java.lang.reflect.Method;

public interface ProxyProvider {

    void before(Object proxy, Method method, Object[] args);

    void after(Object proxy, Method method, Object[] args);

    void afterThrow(Object proxy, Method method, Object[] args, Exception e);

    void doFinally(Object proxy, Method method, Object[] args);
}
