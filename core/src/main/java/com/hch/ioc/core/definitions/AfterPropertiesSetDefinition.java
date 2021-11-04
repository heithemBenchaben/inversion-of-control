package com.hch.ioc.core.definitions;

import java.lang.reflect.Method;

public class AfterPropertiesSetDefinition {

    private Method method;

    public AfterPropertiesSetDefinition(Method method) {
        this.method = method;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
