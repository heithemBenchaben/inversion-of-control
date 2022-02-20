package com.hch.ioc.core.workers.impl;

import com.hch.ioc.core.definitions.IocScanDefinition;
import com.hch.ioc.core.registries.BeanDefinitionRegistry;
import com.hch.ioc.core.registries.PropertiesRegistry;
import com.hch.ioc.core.workers.Worker;

import java.util.HashMap;
import java.util.Map;

public class CleanUpWorker implements Worker {

    public static final String ACTIVE_PROFILES = "active.profiles";
    public static final String DEFAULT = "default";

    private static CleanUpWorker cleanUpWorker;

    private CleanUpWorker() {
    }

    public static CleanUpWorker getInstance() {
        if (cleanUpWorker == null) {
            cleanUpWorker = new CleanUpWorker();
        }
        return cleanUpWorker;
    }

    @Override
    public void start() {
        Map<String, IocScanDefinition> registryMap = new HashMap<>();
        BeanDefinitionRegistry
                .getInstance()
                .getRegistry()
                .forEach(
                        (key, value) -> {
                            if (checkPropertyForConditionalOn(value) && checkMatchingProfile(value)) {
                                registryMap.put(key, value);
                            }
                        }
                );
        // set clean registry
        BeanDefinitionRegistry.getInstance().setRegistry(registryMap);
        System.out.println();
    }

    private boolean checkPropertyForConditionalOn(IocScanDefinition iocScanDefinition) {
        if (iocScanDefinition.getConditionalOnDefinition() != null) {
            String property = iocScanDefinition.getConditionalOnDefinition().getConditionalOn().property();
            String having = iocScanDefinition.getConditionalOnDefinition().getConditionalOn().having();
            return PropertiesRegistry.getInstance().getProperties().get(property).equals(having);
        } else return true;
    }

    public boolean checkMatchingProfile(IocScanDefinition iocScanDefinition) {
        // retrieve configured profiles from properties
        String configuredProfiles = PropertiesRegistry
                .getInstance()
                .getProperties()
                .get(ACTIVE_PROFILES);
        // if configuredProfiles is not configured so active profile is set to default
        // if configuredProfiles is configured so accept this profile and the default one
        String activeProfiles = (configuredProfiles == null ? DEFAULT : configuredProfiles.concat(DEFAULT));

        // filter registry to accept only match profiles
        return checkMatchingProfile(iocScanDefinition, activeProfiles);
    }

    // check if iocScanDefinition match the active profiles
    private boolean checkMatchingProfile(IocScanDefinition iocScanDefinition, String activeProfiles) {
        return iocScanDefinition
                .getProfiles()
                .stream()
                .anyMatch(profile ->
                        activeProfiles.contains(profile)
                );
    }
}
