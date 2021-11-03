package com.hch.ioc.core.scanners.impl;

import com.hch.ioc.core.annotations.ConditionalOnMissingBean;
import com.hch.ioc.core.definitions.IocScanDefinition;
import com.hch.ioc.core.definitions.ConditionalOnMissingBeanDefinition;
import com.hch.ioc.core.scanners.Scanner;
import com.hch.ioc.core.utils.ContainerUtils;

import java.lang.annotation.Annotation;
import java.util.Optional;

public class ConditionalOnMissingBeanScanner implements Scanner {

    @Override
    public void doScan(IocScanDefinition iocScanDefinition) {
        Optional<Annotation> optionalAnnotation = ContainerUtils.findAnnotation(iocScanDefinition.getClazz(), ConditionalOnMissingBean.class);
        if(optionalAnnotation.isPresent()){
            iocScanDefinition.setConditionalOnMissingBeanDefinition(new ConditionalOnMissingBeanDefinition(optionalAnnotation.get()));
        }
    }
}