package com.hch.ioc.core.scanners.impl;

import com.hch.ioc.core.annotations.Proxy;
import com.hch.ioc.core.definitions.IocScanDefinition;
import com.hch.ioc.core.definitions.ProxyDefinition;
import com.hch.ioc.core.scanners.Scanner;
import com.hch.ioc.core.utils.Utils;

import java.lang.annotation.Annotation;
import java.util.Optional;

public class ProxyScanner implements Scanner {

    private static ProxyScanner proxyScanner;

    private ProxyScanner() {
    }

    public static ProxyScanner getInstance() {
        if (proxyScanner == null) {
            proxyScanner = new ProxyScanner();
        }
        return proxyScanner;
    }

    /**
     * find @IocCacheable under clazz
     * if exist build a ProxyDefinition based on @IocCacheable found
     *
     * @param iocScanDefinition
     */
    @Override
    public void doScan(IocScanDefinition iocScanDefinition) {
        Optional<Annotation> optionalAnnotation = Utils.findAnnotation(iocScanDefinition.getClazz(), Proxy.class);
        if (optionalAnnotation.isPresent()) {
            Proxy proxy = (Proxy) optionalAnnotation.get();
            iocScanDefinition.setProxyDefinition(new ProxyDefinition(proxy));
            iocScanDefinition.setProxied(Boolean.TRUE);
        }
    }
}
