package com.hch.ioc.core.workers.impl;

import com.hch.ioc.core.definitions.IocScanDefinition;
import com.hch.ioc.core.registries.BeanDefinitionRegistry;
import com.hch.ioc.core.registries.IocScanClazzRegistry;
import com.hch.ioc.core.scanners.template.ScannerTemplateProvider;
import com.hch.ioc.core.workers.Worker;

public class BeanDefinitionWorker implements Worker {


    /**
     * build iocScanDefinition for every class annotated by IocScan and fill the BeanDefinitionRegistry
     */
    @Override
    public void start() {
        // build iocScanDefinitionMap
        startBuildingBeanDefinitionRegistry();
    }


    private void startBuildingBeanDefinitionRegistry() {
        // loop over all classes annotated by IocScan
        IocScanClazzRegistry
                .getInstance()
                .getIocScanClazzList()
                .stream()
                .forEach(clazz -> {
                    // build iocScanDefinition for clazz
                    IocScanDefinition iocScanDefinition = doScans(clazz);
                    // put the iocScanDefinition into the correct map in BeanDefinitionRegistry
                    if ((iocScanDefinition.getConditionalOnMissingBeanDefinition() == null)) {
                        // put under registry
                        BeanDefinitionRegistry.getInstance().getRegistry().put(iocScanDefinition.getClazz().getName(), iocScanDefinition);
                    } else {
                        // put under conditionalOnMissingBeanRegistry
                        BeanDefinitionRegistry.getInstance().getConditionalOnMissingBeanRegistry().put(iocScanDefinition.getClazz().getName(), iocScanDefinition);
                    }
                });
    }

    /**
     * build IocScanDefinition for clazz based on list of Scanner
     *
     * @param clazz
     * @return
     */
    private static IocScanDefinition doScans(Class<?> clazz) {
        // instantiate the iocScanDefinition for the target clazz
        IocScanDefinition iocScanDefinition = new IocScanDefinition(clazz);
        // loop over all scanners and update the iocScanDefinition
        ScannerTemplateProvider.doScans(iocScanDefinition);
        // return the iocScanDefinition as result after applying all scanner
        return iocScanDefinition;
    }
}