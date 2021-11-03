package com.hch.ioc.core;

import com.hch.ioc.core.workers.template.WorkerTemplateProvider;

public class IocRunner {

    public static void run() {

        System.out.println(" ========= start ioc runner ========= ");

        start();

        System.out.println(" ========= registries configured successfully ========= ");
    }

    private static void start() {
        WorkerTemplateProvider.start();
    }
}
