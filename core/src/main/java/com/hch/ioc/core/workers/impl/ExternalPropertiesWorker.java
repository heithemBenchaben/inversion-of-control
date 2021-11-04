package com.hch.ioc.core.workers.impl;

import com.hch.ioc.core.registries.PropertiesRegistry;
import com.hch.ioc.core.utils.ContainerUtils;
import com.hch.ioc.core.workers.Worker;

import java.util.HashMap;
import java.util.Map;

public class ExternalPropertiesWorker implements Worker {

    public static final String IOC_PROPERTIES = "ioc.properties";

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
        ContainerUtils.getResources(IOC_PROPERTIES)
                .stream()
                .forEach(url -> ContainerUtils.fillPropertiesMap(url, propertiesMap));
        return propertiesMap;
    }

}
