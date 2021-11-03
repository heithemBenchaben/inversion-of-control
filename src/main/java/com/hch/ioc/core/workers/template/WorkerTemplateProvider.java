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
        buildTemplate()
                .forEach(worker ->
                        worker.start()
                );
    }

    /**
     * build the Worker list
     * every item in this list will be responsible to fill a target registry
     *
     * @return
     */
    private static List<Worker> buildTemplate() {
        return Arrays
                .asList(
                        new ConfigurationWorker(),
                        new ScanPathWorker(),
                        new ExternalPropertiesWorker(),
                        new IocScanWorker(),
                        new BeanDefinitionWorker(),
                        new BeanWorker(),
                        new ShutdownHookWorker()
                );
    }
}
