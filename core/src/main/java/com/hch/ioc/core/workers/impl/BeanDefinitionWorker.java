package com.hch.ioc.core.workers.impl;

import com.hch.ioc.core.definitions.IocScanDefinition;
import com.hch.ioc.core.registries.BeanDefinitionRegistry;
import com.hch.ioc.core.registries.IocScanClazzRegistry;
import com.hch.ioc.core.scanners.provider.ScannerChainProvider;
import com.hch.ioc.core.workers.Worker;

public class BeanDefinitionWorker implements Worker {

    private static BeanDefinitionWorker beanDefinitionWorker;

    private BeanDefinitionWorker() {
    }

    public static BeanDefinitionWorker getInstance() {
        if (beanDefinitionWorker == null) {
            beanDefinitionWorker = new BeanDefinitionWorker();
        }
        return beanDefinitionWorker;
    }


    /**
     * build iocScanDefinition for every class annotated by IocScan and fill the BeanDefinitionRegistry
     */
    @Override
    public void start() {
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
                        // put under the global registry
                        BeanDefinitionRegistry.getInstance().getRegistry().put(iocScanDefinition.getClazz().getName(), iocScanDefinition);
                    } else {
                        // put under conditional on missing bean registry
                        BeanDefinitionRegistry.getInstance().getConditionalOnMissingBeanRegistry().put(iocScanDefinition.getClazz().getName(), iocScanDefinition);
                    }
                });
    }

    /**
     * build IocScanDefinition for clazz based on list of Scanners
     *
     * @param clazz
     * @return
     */
    private static IocScanDefinition doScans(Class<?> clazz) {
        // instantiate the iocScanDefinition for the target clazz
        IocScanDefinition iocScanDefinition = new IocScanDefinition(clazz);
        // loop over all scanners and update the iocScanDefinition
        ScannerChainProvider.doScans(iocScanDefinition);
        // return the iocScanDefinition as result after applying all scanners
        return iocScanDefinition;
    }
}