package com.hch.ioc.core.scanners.impl;

import com.hch.ioc.core.annotations.ConditionalOn;
import com.hch.ioc.core.definitions.ConditionalOnDefinition;
import com.hch.ioc.core.definitions.IocScanDefinition;
import com.hch.ioc.core.scanners.Scanner;
import com.hch.ioc.core.utils.ContainerUtils;

import java.lang.annotation.Annotation;
import java.util.Optional;

public class ConditionalOnScanner implements Scanner {

    @Override
    public void doScan(IocScanDefinition iocScanDefinition) {
        Optional<Annotation> optionalAnnotation = ContainerUtils.findAnnotation(iocScanDefinition.getClazz(), ConditionalOn.class);
        if (optionalAnnotation.isPresent()) {
            iocScanDefinition.setConditionalOnDefinition(new ConditionalOnDefinition(optionalAnnotation.get()));
        }
    }
}
