package com.hch.ioc.test.beans.impl;

import com.hch.ioc.core.annotations.IocInject;
import com.hch.ioc.core.annotations.IocScan;
import com.hch.ioc.test.beans.I2;
import com.hch.ioc.test.beans.I3;
import com.hch.ioc.test.beans.I4;

@IocScan
public class D implements I4 {
    @IocInject
    private I2 i2;
    @IocInject
    private I3 i3;

    public I2 getI2() {
        return i2;
    }

    public void setI2(I2 i2) {
        this.i2 = i2;
    }

    public I3 getI3() {
        return i3;
    }

    public void setI3(I3 i3) {
        this.i3 = i3;
    }

}
