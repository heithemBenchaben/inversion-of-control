package com.hch.ioc.core.registries;

import java.util.HashMap;
import java.util.Map;

public class PropertiesRegistry {

    private static PropertiesRegistry propertiesRegistry;

    private Map<String, String> properties;

    public static synchronized PropertiesRegistry getInstance() {
        if (propertiesRegistry == null)
            propertiesRegistry = new PropertiesRegistry();
        return propertiesRegistry;
    }

    public PropertiesRegistry() {
        this.properties = new HashMap<>();
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }
}
