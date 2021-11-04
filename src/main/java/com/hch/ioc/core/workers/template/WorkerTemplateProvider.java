package com.hch.ioc.core.workers.template;

import com.hch.ioc.core.workers.Worker;
import com.hch.ioc.core.workers.impl.*;

import java.util.Arrays;
import java.util.List;

public class WorkerTemplateProvider {

    private static List<Worker> workers;

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
        if (workers == null) {
            workers = Arrays
                    .asList(
                            new ConfigurationWorker(),
                            new ScanPathWorker(),
                            new ExternalPropertiesWorker(),
                            new IocScanWorker(),
                            new BeanDefinitionWorker(),
                            new BeanDefinitionRegistryWorker(),
                            new BeanWorker(),
                            new ShutdownHookWorker()
                    );
        }
        return workers;
    }
}
