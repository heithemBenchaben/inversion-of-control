package com.hch.ioc.core.scanners.impl;

import com.hch.ioc.core.annotations.IocCacheable;
import com.hch.ioc.core.definitions.CacheableDefinition;
import com.hch.ioc.core.definitions.IocScanDefinition;
import com.hch.ioc.core.scanners.Scanner;
import com.hch.ioc.core.utils.ContainerUtils;

import java.lang.annotation.Annotation;
import java.util.Optional;

public class IocCacheableScanner implements Scanner {

    private static IocCacheableScanner iocCacheableScanner;

    private IocCacheableScanner() {
    }

    public static IocCacheableScanner getInstance() {
        if (iocCacheableScanner == null) {
            iocCacheableScanner = new IocCacheableScanner();
        }
        return iocCacheableScanner;
    }

    /**
     * find @IocCacheable under clazz
     * if exist build a CacheableDefinition based on @IocCacheable found
     * @param iocScanDefinition
     */
    @Override
    public void doScan(IocScanDefinition iocScanDefinition) {
        Optional<Annotation> optionalAnnotation = ContainerUtils.findAnnotation(iocScanDefinition.getClazz(), IocCacheable.class);
        if (optionalAnnotation.isPresent()) {
            IocCacheable iocCacheable = (IocCacheable) optionalAnnotation.get();
            iocScanDefinition.setCacheableDefinition(new CacheableDefinition(iocCacheable));
        }
    }
}
