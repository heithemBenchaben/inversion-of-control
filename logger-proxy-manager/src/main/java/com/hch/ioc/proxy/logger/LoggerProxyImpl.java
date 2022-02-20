package com.hch.ioc.proxy.logger;

import com.hch.ioc.core.annotations.IocScan;
import com.hch.ioc.core.exceptions.SimpleIocException;
import com.hch.ioc.core.proxies.LoggerProxyProvider;

import java.lang.reflect.Method;

@IocScan
public class LoggerProxyImpl implements LoggerProxyProvider {

    @Override
    public void before(Object proxy, Method method, Object[] args) {
        System.out.println("LOGGER :: before invoke " + method.getName());
    }

    @Override
    public void after(Object proxy, Method method, Object[] args) {
        System.out.println("LOGGER :: after invoke " + method.getName());
    }

    @Override
    public void afterThrow(Object proxy, Method method, Object[] args, Exception e) {
        System.out.println("LOGGER :: exception while invoke " + method.getName());
        throw new SimpleIocException(e);
    }

    @Override
    public void doFinally(Object proxy, Method method, Object[] args) {
        System.out.println("LOGGER :: finally invoke " + method.getName());
    }
}
