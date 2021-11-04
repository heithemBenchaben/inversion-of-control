package com.hch.ioc.core.scanners.impl;

import com.hch.ioc.core.definitions.IocScanDefinition;
import com.hch.ioc.core.scanners.Scanner;

import java.util.Arrays;

public class TypeScanner implements Scanner {

    /**
     * fetch all interfaces that clazz implements
     * set types in the iocScanDefinition
     *
     * @param iocScanDefinition
     */
    @Override
    public void doScan(IocScanDefinition iocScanDefinition) {
        iocScanDefinition.getTypes().addAll(
                Arrays.asList(iocScanDefinition.getClazz().getInterfaces())
        );
    }

}
