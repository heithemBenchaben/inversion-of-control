package com.hch.ioc.core.processors.definition.impl;

import com.hch.ioc.core.definitions.IocScanDefinition;
import com.hch.ioc.core.processors.definition.BeanDefinitionProcessor;
import com.hch.ioc.core.registries.BeanDefinitionRegistry;
import com.hch.ioc.core.registries.PropertiesRegistry;

import java.util.HashMap;
import java.util.Map;

public class ProfileProcessor implements BeanDefinitionProcessor {

    public static final String ACTIVE_PROFILES = "active.profiles";
    public static final String DEFAULT = "default";

    private static ProfileProcessor profileProcessor;

    private ProfileProcessor() {
    }

    public static ProfileProcessor getInstance() {
        if (profileProcessor == null) {
            profileProcessor = new ProfileProcessor();
        }
        return profileProcessor;
    }

    @Override
    public void process() {
        processProfile();
    }

    private void processProfile() {
        // initialize clean up map
        Map<String, IocScanDefinition> cleanUpRegistryResult = new HashMap<>();
        // retrieve configured profiles from properties
        String configuredProfiles = PropertiesRegistry
                .getInstance()
                .getProperties()
                .get(ACTIVE_PROFILES);
        // if configuredProfiles is not configured so active profile is set to default
        // if configuredProfiles is configured so accept this profile and the default one
        String activeProfiles = (configuredProfiles == null ? DEFAULT : configuredProfiles.concat(DEFAULT));

        // filter registry to accept only match profiles
        BeanDefinitionRegistry
                .getInstance()
                .getRegistry()
                .entrySet()
                .stream()
                .forEach(keyValue -> {
                            if (checkMatchingProfile(keyValue.getValue(), activeProfiles)) {
                                cleanUpRegistryResult.put(keyValue.getKey(), keyValue.getValue());
                            }
                        }
                );
        // set the clean up map to the registry
        BeanDefinitionRegistry.getInstance().setRegistry(cleanUpRegistryResult);
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
