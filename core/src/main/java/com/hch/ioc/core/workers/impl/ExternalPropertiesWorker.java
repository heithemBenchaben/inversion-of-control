package com.hch.ioc.core.workers.impl;

import com.hch.ioc.core.registries.PropertiesRegistry;
import com.hch.ioc.core.utils.Utils;
import com.hch.ioc.core.workers.Worker;

import java.util.HashMap;
import java.util.Map;

public class ExternalPropertiesWorker implements Worker {

    public static final String IOC_PROPERTIES = "ioc.properties";

    private static ExternalPropertiesWorker externalPropertiesWorker;

    private ExternalPropertiesWorker() {
    }

    public static ExternalPropertiesWorker getInstance() {
        if (externalPropertiesWorker == null) {
            externalPropertiesWorker = new ExternalPropertiesWorker();
        }
        return externalPropertiesWorker;
    }

    /**
     * fill properties in the PropertiesRegistry
     */
    @Override
    public void start() {
        PropertiesRegistry
                .getInstance()
                .setProperties(
                        loadAllProperties()
                );
    }

    /**
     * load all properties found in ioc.properties files
     *
     * @return
     */
    private Map<String, String> loadAllProperties() {
        Map<String, String> propertiesMap = new HashMap<>();
        Utils
                .getResources(IOC_PROPERTIES)
                .stream()
                .forEach(url ->
                        Utils.fillPropertiesMap(url, propertiesMap)
                );
        return propertiesMap;
    }

}
