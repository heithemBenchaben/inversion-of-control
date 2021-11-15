package com.hch.ioc.core.scanners.impl;

import com.hch.ioc.core.annotations.ConditionalOnMissingBean;
import com.hch.ioc.core.definitions.ConditionalOnMissingBeanDefinition;
import com.hch.ioc.core.definitions.IocScanDefinition;
import com.hch.ioc.core.scanners.Scanner;
import com.hch.ioc.core.utils.ContainerUtils;

import java.lang.annotation.Annotation;
import java.util.Optional;

public class ConditionalOnMissingBeanScanner implements Scanner {

    private static ConditionalOnMissingBeanScanner conditionalOnMissingBeanScanner;

    private ConditionalOnMissingBeanScanner() {
    }

    public static ConditionalOnMissingBeanScanner getInstance() {
        if (conditionalOnMissingBeanScanner == null) {
            conditionalOnMissingBeanScanner = new ConditionalOnMissingBeanScanner();
        }
        return conditionalOnMissingBeanScanner;
    }

    @Override
    public void doScan(IocScanDefinition iocScanDefinition) {
        Optional<Annotation> optionalAnnotation = ContainerUtils.findAnnotation(iocScanDefinition.getClazz(), ConditionalOnMissingBean.class);
        if (optionalAnnotation.isPresent()) {
            ConditionalOnMissingBean conditionalOnMissingBean = (ConditionalOnMissingBean) optionalAnnotation.get();
            iocScanDefinition.setConditionalOnMissingBeanDefinition(new ConditionalOnMissingBeanDefinition(conditionalOnMissingBean));
        }
    }
}
