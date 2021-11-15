package com.hch.ioc.core.workers.template;

import com.hch.ioc.core.workers.Worker;
import com.hch.ioc.core.workers.impl.*;

import java.util.Arrays;
import java.util.List;

public class WorkerTemplateProvider {
    /**
     * build the Worker list
     * fill target registries by invoking start() for each worker
     */
    public static void start() {
        getWorkers()
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
    private static List<Worker> getWorkers() {
        return Arrays
                .asList(
                        ConfigurationWorker.getInstance(),
                        ScanPathWorker.getInstance(),
                        ExternalPropertiesWorker.getInstance(),
                        IocScanWorker.getInstance(),
                        BeanDefinitionWorker.getInstance(),
                        BeanDefinitionRegistryWorker.getInstance(),
                        BeanWorker.getInstance(),
                        ShutdownHookWorker.getInstance()
                );
    }
}
