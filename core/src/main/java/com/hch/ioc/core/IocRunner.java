package com.hch.ioc.core;

import com.hch.ioc.core.exceptions.SimpleIocException;
import com.hch.ioc.core.workers.provider.WorkerChainProvider;

public class IocRunner {

    public static void run() {

        System.out.println(" ========= start ioc runner ========= ");

        start();

        System.out.println(" ========= registries configured successfully ========= ");
    }

    private static void start() {
        try {
            WorkerChainProvider.start();
        } catch (Exception e) {
            throw new SimpleIocException(e);
        }
    }
}
