package com.hch.ioc.core.registries;

import java.util.ArrayList;
import java.util.List;

public class ScanPathRegistry {

    private static ScanPathRegistry scanPathRegistry;

    private List<String> scanPathList;

    private ScanPathRegistry() {
        this.scanPathList = new ArrayList<>();
    }

    public static ScanPathRegistry getInstance() {
        if (scanPathRegistry == null) {
            scanPathRegistry = new ScanPathRegistry();
        }
        return scanPathRegistry;
    }

    public List<String> getScanPathList() {
        return scanPathList;
    }

    public void setScanPathList(List<String> scanPathList) {
        this.scanPathList = scanPathList;
    }
}
