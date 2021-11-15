package com.hch.ioc.core.definitions;

import com.hch.ioc.core.annotations.IocCacheable;

public class CacheableDefinition {

    private IocCacheable iocCacheable;

    public CacheableDefinition(IocCacheable iocCacheable) {
        this.iocCacheable = iocCacheable;
    }

    public IocCacheable getIocCacheable() {
        return iocCacheable;
    }

    public void setIocCacheable(IocCacheable iocCacheable) {
        this.iocCacheable = iocCacheable;
    }
}
