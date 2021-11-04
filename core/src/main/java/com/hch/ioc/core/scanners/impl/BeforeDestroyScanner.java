package com.hch.ioc.core.scanners.impl;

import com.hch.ioc.core.annotations.BeforeDestroy;
import com.hch.ioc.core.definitions.BeforeDestroyDefinition;
import com.hch.ioc.core.definitions.IocScanDefinition;
import com.hch.ioc.core.scanners.Scanner;
import com.hch.ioc.core.utils.ContainerUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

public class BeforeDestroyScanner implements Scanner {
    /**
     * scan clazz
     * find method annotated by @BeforeDestroy under clazz
     * build BeforeDestroyDefinition
     * set BeforeDestroyDefinition in iocScanDefinition
     *
     * @param iocScanDefinition
     */
    @Override
    public void doScan(IocScanDefinition iocScanDefinition) {
        // find method annotated by @BeforeDestroy under clazz
        Optional<Method> method = findMethodAnnotatedByBeforeDestroy(iocScanDefinition.getClazz());
        if (method.isPresent()) {
            iocScanDefinition.setBeforeDestroyDefinition(
                    // build AfterPropertiesSetDefinition
                    new BeforeDestroyDefinition(method.get())
            );
        }
    }

    /**
     * find method annotated by @BeforeDestroy under clazz
     *
     * @param clazz
     * @return
     */
    private Optional<Method> findMethodAnnotatedByBeforeDestroy(Class<?> clazz) {
        return ContainerUtils
                .findMethodsAnnotatedBy(
                        Arrays.asList(clazz.getDeclaredMethods()), BeforeDestroy.class
                )
                .stream()
                .findFirst();
    }

}
