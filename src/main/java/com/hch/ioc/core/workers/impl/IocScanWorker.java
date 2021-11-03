package com.hch.ioc.core.workers.impl;

import com.hch.ioc.core.annotations.IocScan;
import com.hch.ioc.core.workers.Worker;
import com.hch.ioc.core.registries.IocScanClazzRegistry;
import com.hch.ioc.core.registries.ScanPathRegistry;
import com.hch.ioc.core.utils.ContainerUtils;

import java.util.List;
import java.util.stream.Collectors;

public class IocScanWorker implements Worker {

    /**
     * fill Ioc scan classes in the IocScanClazzRegistry
     */
    @Override
    public void start() {
        IocScanClazzRegistry
                .getInstance()
                // add found classes to IocScanClazzRegistry
                .setIocScanClazzList(
                        // find all classes annotated by @IocScan
                        findAllClazzToBeScan()
                );
    }

    // find all classes annotated by IocScan in all registered path under PathRegistry
    private List<Class<?>> findAllClazzToBeScan() {
        // loop over all package to be scanned and find clazz annotated by @IocScan
        return ScanPathRegistry
                .getInstance()
                .getScanPathList()
                .stream()
                .map(path ->
                        ContainerUtils
                                .findAllClazzAnnotatedBy(path, IocScan.class)
                )
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }
}
