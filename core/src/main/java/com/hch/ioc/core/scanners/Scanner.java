package com.hch.ioc.core.scanners;

import com.hch.ioc.core.definitions.IocScanDefinition;

public interface Scanner {

    void doScan(IocScanDefinition iocScanDefinition);
}
