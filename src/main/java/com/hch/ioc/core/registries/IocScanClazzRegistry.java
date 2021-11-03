package com.hch.ioc.core.registries;

import java.util.ArrayList;
import java.util.List;

public class IocScanClazzRegistry {

    private static IocScanClazzRegistry iocScanClazzRegistry;

    private List<Class<?>> iocScanClazzList;

    public static synchronized IocScanClazzRegistry getInstance() {
        if (iocScanClazzRegistry == null) {
            iocScanClazzRegistry = new IocScanClazzRegistry();
        }
        return iocScanClazzRegistry;
    }

    private IocScanClazzRegistry() {
        iocScanClazzList = new ArrayList<>();
    }

    public List<Class<?>> getIocScanClazzList() {
        return iocScanClazzList;
    }

    public void setIocScanClazzList(List<Class<?>> iocScanClazzList) {
        this.iocScanClazzList = iocScanClazzList;
    }
}
