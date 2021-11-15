package com.hch.ioc.core.scanners.template;

import com.hch.ioc.core.definitions.IocScanDefinition;
import com.hch.ioc.core.scanners.Scanner;
import com.hch.ioc.core.scanners.impl.*;

import java.util.Arrays;
import java.util.List;

public class ScannerTemplateProvider {

    public static void doScans(IocScanDefinition iocScanDefinition) {
        getScanners()
                .forEach(scanner ->
                        // each scanner will update the iocScanDefinition based on it's criteria
                        scanner.doScan(iocScanDefinition)
                );
    }

    /**
     * build list of Scanner
     * every item in this list is responsible for fetch criteria in a class annotated by IocScan
     *
     * @return List<Scanner>
     */
    public static List<Scanner> getScanners() {
        return
                Arrays.asList(
                        TypeScanner.getInstance(),
                        IocProfileScanner.getInstance(),
                        IocScopeScanner.getInstance(),
                        IocCacheableScanner.getInstance(),
                        IocInjectScanner.getInstance(),
                        ExternalPropertyScanner.getInstance(),
                        AfterPropertiesSetScanner.getInstance(),
                        BeforeDestroyScanner.getInstance(),
                        ConditionalOnMissingBeanScanner.getInstance(),
                        ConditionalOnScanner.getInstance()
                );
    }
}
