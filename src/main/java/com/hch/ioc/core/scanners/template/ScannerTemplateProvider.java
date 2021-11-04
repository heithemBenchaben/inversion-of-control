package com.hch.ioc.core.scanners.template;

import com.hch.ioc.core.definitions.IocScanDefinition;
import com.hch.ioc.core.scanners.Scanner;
import com.hch.ioc.core.scanners.impl.*;

import java.util.Arrays;
import java.util.List;

public class ScannerTemplateProvider {

    private static List<Scanner> scanners;

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
        if (scanners == null) {
            scanners = Arrays.asList(
                    new TypeScanner(),
                    new IocProfileScanner(),
                    new IocScopeScanner(),
                    new IocInjectScanner(),
                    new ExternalPropertyScanner(),
                    new AfterPropertiesSetScanner(),
                    new BeforeDestroyScanner(),
                    new ConditionalOnMissingBeanScanner(),
                    new ConditionalOnScanner()
            );
        }
        return scanners;
    }
}
