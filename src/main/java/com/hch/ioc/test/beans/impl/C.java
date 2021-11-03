package com.hch.ioc.test.beans.impl;

import com.hch.ioc.core.annotations.BeforeDestroy;
import com.hch.ioc.core.annotations.IocInject;
import com.hch.ioc.core.annotations.IocScan;
import com.hch.ioc.test.beans.I2;
import com.hch.ioc.test.beans.I3;

@IocScan
public class C implements I3 {

    @IocInject
    private I2 i2;

    @BeforeDestroy
    public void beforeDestroy() {
        System.out.println("beforeDestroy :: C");
    }

    public I2 getI2() {
        return i2;
    }

    public void setI2(I2 i2) {
        this.i2 = i2;
    }
}
