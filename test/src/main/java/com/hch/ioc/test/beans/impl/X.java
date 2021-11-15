package com.hch.ioc.test.beans.impl;

import com.hch.ioc.core.annotations.IocCacheable;
import com.hch.ioc.core.annotations.IocScan;
import com.hch.ioc.test.beans.I10;

@IocScan
@IocCacheable
public class X implements I10 {

    @Override
    public void test1(String param1, Integer param2) {
        System.out.println("param1 = " + param1 + ", param2 = " + param2);
    }

    @Override
    public void test2(String param1, Integer param2) {
        System.out.println("param1 = " + param1 + ", param2 = " + param2);
    }
}
