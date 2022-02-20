package com.hch.ioc.core.workers.provider;

import com.hch.ioc.core.workers.Worker;
import com.hch.ioc.core.workers.impl.*;

import java.util.Arrays;
import java.util.List;

public class WorkerChainProvider {
    /**
     * build the Worker chain
     * fill target registries by invoking start() for each worker
     */
    public static void start() {
        buildWorkerChain()
                .forEach(worker ->
                        worker.start()
                );
    }

    /**
     * get the Worker list
     * every item in this list will be responsible to fill a target registry
     *
     * @return
     */
    private static List<Worker> buildWorkerChain() {
        return Arrays
                .asList(
                        ConfigurationWorker.getInstance(),
                        ScanPathWorker.getInstance(),
                        ExternalPropertiesWorker.getInstance(),
                        IocScanWorker.getInstance(),
                        BeanDefinitionWorker.getInstance(),
                        CleanUpWorker.getInstance(),
                        ConditionalOnMissingBeanWorker.getInstance(),
                        HierarchyBeanDefinitionsWorker.getInstance(),
                        BeanWorker.getInstance(),
                        ShutdownHookWorker.getInstance()
                );
    }
}
