package com.hch.ioc.core.scanners.impl;

import com.hch.ioc.core.annotations.IocInject;
import com.hch.ioc.core.definitions.IocInjectDefinition;
import com.hch.ioc.core.definitions.IocScanDefinition;
import com.hch.ioc.core.scanners.Scanner;
import com.hch.ioc.core.utils.ContainerUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class IocInjectScanner implements Scanner {

    private static IocInjectScanner iocInjectScanner;

    private IocInjectScanner() {
    }

    public static IocInjectScanner getInstance() {
        if (iocInjectScanner == null) {
            iocInjectScanner = new IocInjectScanner();
        }
        return iocInjectScanner;
    }

    /**
     * scan a clazz
     * fetch all fields annotated by @IocInject in the target clazz
     * build IocInjectDefinition for each field
     * set the IocInjectDefinitions in the iocScanDefinition
     *
     * @param iocScanDefinition
     */
    @Override
    public void doScan(IocScanDefinition iocScanDefinition) {
        iocScanDefinition
                .setIocInjectDefinitions(
                        buildIocInjectDefinitions(iocScanDefinition.getClazz())
                );
    }

    /**
     * fetch fields annotated by IocInject and build an IocInjectDefinition for each field
     *
     * @param clazz
     * @return
     */
    public List<IocInjectDefinition> buildIocInjectDefinitions(Class<?> clazz) {
        return ContainerUtils
                // find fields annotated by IocInject under clazz
                .findFieldsAnnotatedBy(
                        Arrays.asList(clazz.getDeclaredFields())
                        , IocInject.class)
                .stream()
                .map(field ->
                        // build IocInjectDefinition for each field
                        new IocInjectDefinition(field)
                ).collect(Collectors.toList());
    }
}
