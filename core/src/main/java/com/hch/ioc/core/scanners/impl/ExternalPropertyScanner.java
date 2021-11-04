package com.hch.ioc.core.scanners.impl;

import com.hch.ioc.core.annotations.ExternalProperty;
import com.hch.ioc.core.definitions.ExternalPropertyDefinition;
import com.hch.ioc.core.definitions.IocScanDefinition;
import com.hch.ioc.core.scanners.Scanner;
import com.hch.ioc.core.utils.ContainerUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ExternalPropertyScanner implements Scanner {

    /**
     * scan clazz
     * fetch for fields annotated by @ExternalProperty
     * build externalPropertyDefinitions
     * set externalPropertyDefinitions in the iocScanDefinition
     *
     * @param iocScanDefinition
     */
    @Override
    public void doScan(IocScanDefinition iocScanDefinition) {
        iocScanDefinition
                .setExternalPropertyDefinitions(
                        buildExternalPropertyDefinitions(iocScanDefinition)
                );
    }

    private List<ExternalPropertyDefinition> buildExternalPropertyDefinitions(IocScanDefinition iocScanDefinition) {
        return ContainerUtils
                // find fields annotated by @ExternalProperty under clazz
                .findFieldsAnnotatedBy(
                        Arrays.asList(iocScanDefinition.getClazz().getDeclaredFields())
                        , ExternalProperty.class)
                .stream()
                .map(field ->
                        // build ExternalPropertyDefinition for field
                        new ExternalPropertyDefinition(field, field.getAnnotation(ExternalProperty.class).value())
                ).collect(Collectors.toList());
    }
}
