package com.hch.ioc.core.processors.bean.context;

import com.hch.ioc.core.definitions.IocScanDefinition;

public class BeanProcessContext {
    private IocScanDefinition iocScanDefinition;
    private Object object;

    public IocScanDefinition getIocScanDefinition() {
        return iocScanDefinition;
    }

    public BeanProcessContext(IocScanDefinition iocScanDefinition) {
        this.iocScanDefinition = iocScanDefinition;
    }

    public void setIocScanDefinition(IocScanDefinition iocScanDefinition) {
        this.iocScanDefinition = iocScanDefinition;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
