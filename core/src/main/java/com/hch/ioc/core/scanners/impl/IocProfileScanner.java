package com.hch.ioc.core.scanners.impl;

import com.hch.ioc.core.annotations.IocProfile;
import com.hch.ioc.core.definitions.IocScanDefinition;
import com.hch.ioc.core.scanners.Scanner;
import com.hch.ioc.core.utils.ContainerUtils;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class IocProfileScanner implements Scanner {

    /**
     * set profiles in the iocScanDefinition if exist
     *
     * @param iocScanDefinition
     */
    @Override
    public void doScan(IocScanDefinition iocScanDefinition) {
        iocScanDefinition.setProfiles(
                findProfilesIfExist(iocScanDefinition.getClazz())
        );
    }

    /**
     * get profiles
     *
     * @param clazz
     * @return
     */
    private List<String> findProfilesIfExist(Class<?> clazz) {
        Optional<Annotation> optionalAnnotation = ContainerUtils.findAnnotation(clazz, IocProfile.class);
        return (optionalAnnotation.isPresent() ? Arrays.asList(((IocProfile) optionalAnnotation.get()).profiles()) : Arrays.asList("default"));
    }
}
