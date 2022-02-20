package com.hch.ioc.core.workers.impl;

import com.hch.ioc.core.registries.ConfigurationRegistry;
import com.hch.ioc.core.utils.Utils;
import com.hch.ioc.core.workers.Worker;

import java.util.List;
import java.util.stream.Collectors;

public class ConfigurationWorker implements Worker {

    public static final String CONFIGURATION_FILE_PATTERN = "configuration.txt";

    private static ConfigurationWorker configurationWorker;

    private ConfigurationWorker() {
    }

    public static ConfigurationWorker getInstance() {
        if (configurationWorker == null) {
            configurationWorker = new ConfigurationWorker();
        }
        return configurationWorker;
    }

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
        return Utils
                .getResources(CONFIGURATION_FILE_PATTERN)
                .stream()
                .map(url ->
                        Utils
                                // load configuration Class
                                .loadClazzByFileName(
                                        // read full path of a configuration class from configuration file
                                        Utils.readFileAsString(url)
                                )
                )
                .collect(Collectors.toList());
    }
}