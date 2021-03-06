package com.hch.ioc.core.scanners.impl;

import com.hch.ioc.core.annotations.AfterPropertiesSet;
import com.hch.ioc.core.definitions.AfterPropertiesSetDefinition;
import com.hch.ioc.core.definitions.IocScanDefinition;
import com.hch.ioc.core.scanners.Scanner;
import com.hch.ioc.core.utils.Utils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

public class AfterPropertiesSetScanner implements Scanner {

    private static AfterPropertiesSetScanner afterPropertiesSetScanner;

    private AfterPropertiesSetScanner() {
    }

    public static AfterPropertiesSetScanner getInstance() {
        if (afterPropertiesSetScanner == null) {
            afterPropertiesSetScanner = new AfterPropertiesSetScanner();
        }
        return afterPropertiesSetScanner;
    }

    /**
     * scan clazz
     * find method annotated by @AfterPropertiesSet under clazz
     * build AfterPropertiesSetDefinition
     * set AfterPropertiesSetDefinition in iocScanDefinition
     *
     * @param iocScanDefinition
     */
    @Override
    public void doScan(IocScanDefinition iocScanDefinition) {
        // find method annotated by @AfterPropertiesSet under clazz
        Optional<Method> method = findMethodAnnotatedByAfterPropertiesSet(iocScanDefinition.getClazz());
        if (method.isPresent()) {
            iocScanDefinition.setAfterPropertiesSetDefinition(
                    // build AfterPropertiesSetDefinition
                    new AfterPropertiesSetDefinition(method.get())
            );
        }
    }

    /**
     * find method annotated by @AfterPropertiesSet under clazz
     *
     * @param clazz
     * @return
     */
    private Optional<Method> findMethodAnnotatedByAfterPropertiesSet(Class<?> clazz) {
        return Utils
                .findMethodsAnnotatedBy(
                        Arrays.asList(clazz.getDeclaredMethods()), AfterPropertiesSet.class
                )
                .stream()
                .findFirst();
    }

}
