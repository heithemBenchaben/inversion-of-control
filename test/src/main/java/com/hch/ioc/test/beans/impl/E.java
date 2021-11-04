package com.hch.ioc.test.beans.impl;

import com.hch.ioc.core.annotations.AfterPropertiesSet;
import com.hch.ioc.core.annotations.ConditionalOnMissingBean;
import com.hch.ioc.core.annotations.IocInject;
import com.hch.ioc.core.annotations.IocScan;
import com.hch.ioc.test.beans.I7;

@IocScan
@ConditionalOnMissingBean
public class E implements I7 {

    @IocInject
    private D d;

    public E() {
    }

    @AfterPropertiesSet
    public void afterPropertiesSet() {
        System.out.println("afterPropertiesSet :: E");
    }

    public D getD() {
        return d;
    }

    public void setD(D d) {
        this.d = d;
    }
}
