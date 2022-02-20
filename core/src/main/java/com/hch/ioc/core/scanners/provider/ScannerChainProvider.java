package com.hch.ioc.core.scanners.provider;

import com.hch.ioc.core.definitions.IocScanDefinition;
import com.hch.ioc.core.scanners.Scanner;
import com.hch.ioc.core.scanners.impl.*;

import java.util.Arrays;
import java.util.List;

public class ScannerChainProvider {

    public static void doScans(IocScanDefinition iocScanDefinition) {
        buildScannerChain()
                .forEach(scanner ->
                        // each scanner will update the iocScanDefinition based on it's criteria
                        scanner.doScan(iocScanDefinition)
                );
    }

    /**
     * build list of Scanners
     * every item in this list is responsible for fetching criteria in a class annotated by IocScan
     *
     * @return List<Scanner>
     */
    public static List<Scanner> buildScannerChain() {
        return
                Arrays.asList(
                        TypeScanner.getInstance(),
                        IocProfileScanner.getInstance(),
                        IocScopeScanner.getInstance(),
                        ProxyScanner.getInstance(),
                        IocInjectScanner.getInstance(),
                        ExternalPropertyScanner.getInstance(),
                        AfterPropertiesSetScanner.getInstance(),
                        BeforeDestroyScanner.getInstance(),
                        ConditionalOnMissingBeanScanner.getInstance(),
                        ConditionalOnScanner.getInstance()
                );
    }
}
