package com.hch.ioc.core.registries;

import java.util.ArrayList;
import java.util.List;

public class ConfigurationRegistry {

    private static ConfigurationRegistry configurationRegistry;

    private List<Class<?>> configClazzList;

    private ConfigurationRegistry() {
        this.configClazzList = new ArrayList<>();
    }

    public static ConfigurationRegistry getInstance() {
        if (configurationRegistry == null) {
            configurationRegistry = new ConfigurationRegistry();
        }
        return configurationRegistry;
    }

    public List<Class<?>> getConfigClazzList() {
        return configClazzList;
    }

    public void setConfigClazzList(List<Class<?>> configClazzList) {
        this.configClazzList = configClazzList;
    }
}
