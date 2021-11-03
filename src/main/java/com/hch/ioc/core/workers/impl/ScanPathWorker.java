package com.hch.ioc.core.workers.impl;

import com.hch.ioc.core.annotations.ScanPath;
import com.hch.ioc.core.registries.ConfigurationRegistry;
import com.hch.ioc.core.registries.ScanPathRegistry;
import com.hch.ioc.core.utils.ContainerUtils;
import com.hch.ioc.core.workers.Worker;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ScanPathWorker implements Worker {

    /**
     * fill scan paths in the ScanPathRegistry
     */
    @Override
    public void start() {
        // set scan paths list under scan path registry
        ScanPathRegistry
                .getInstance()
                .setScanPathList(
                        ConfigurationRegistry
                                // get ConfigurationRegistry
                                .getInstance()
                                // get all configuration clazz
                                .getConfigClazzList()
                                .stream()
                                // get paths from annotation
                                .map(clazz ->
                                        // get all paths configured in ScanPath annotation
                                        getScanPathAnnotation(clazz).paths()
                                )
                                // flat map paths
                                .flatMap(Arrays::stream)
                                .collect(Collectors.toList())
                );
    }

    private ScanPath getScanPathAnnotation(Class<?> clazz) {
        return (ScanPath) ContainerUtils.findAnnotation(clazz, ScanPath.class).get();
    }

}