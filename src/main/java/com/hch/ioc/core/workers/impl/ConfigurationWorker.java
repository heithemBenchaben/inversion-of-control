package com.hch.ioc.core.workers.impl;

import com.hch.ioc.core.workers.Worker;
import com.hch.ioc.core.registries.ConfigurationRegistry;
import com.hch.ioc.core.utils.ContainerUtils;

import java.util.List;
import java.util.stream.Collectors;

public class ConfigurationWorker implements Worker {

    public static final String CONFIGURATION_FILE_PATTERN = "configuration.txt";

    /**
     * fill config classes in the ConfigurationRegistry
     */
    @Override
    public void start() {
        // register all classes configured by configuration.txt files
        ConfigurationRegistry
                .getInstance()
                .setConfigClazzList(
                        findAllConfigurationClazz()
                );
    }

    /**
     * find all clazz configured by configuration.txt files
     *
     * @return
     */
    private static List<Class<?>> findAllConfigurationClazz() {
        return ContainerUtils
                .getResources(CONFIGURATION_FILE_PATTERN)
                .stream()
                .map(url ->
                        ContainerUtils
                                // load configuration Class
                                .loadClazzByFileName(
                                        // read full path of a configuration class from configuration file
                                        ContainerUtils.readFileAsString(url)
                                )
                ).collect(Collectors.toList());
    }
}