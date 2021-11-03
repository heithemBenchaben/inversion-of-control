package com.hch.ioc.core.scanners.impl;

import com.hch.ioc.core.annotations.IocScope;
import com.hch.ioc.core.definitions.IocScanDefinition;
import com.hch.ioc.core.enums.Scope;
import com.hch.ioc.core.scanners.Scanner;
import com.hch.ioc.core.utils.ContainerUtils;

import java.lang.annotation.Annotation;
import java.util.Optional;

public class IocScopeScanner implements Scanner {

    /**
     * set scope in the iocScanDefinition
     *
     * @param iocScanDefinition
     */
    @Override
    public void doScan(IocScanDefinition iocScanDefinition) {
        iocScanDefinition.setScope(
                findScope(iocScanDefinition.getClazz())
        );
    }

    /**
     * find scope
     *
     * @param clazz
     * @return
     */
    private Scope findScope(Class<?> clazz) {
        Optional<Annotation> optionalAnnotation = ContainerUtils.findAnnotation(clazz, IocScope.class);
        return (optionalAnnotation.isPresent() ? ((IocScope) optionalAnnotation.get()).scope() : Scope.SINGLETON);
    }
}
