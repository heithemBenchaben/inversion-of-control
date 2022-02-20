package com.hch.ioc.core.definitions;

import com.hch.ioc.core.annotations.Proxy;

public class ProxyDefinition {

    private Proxy proxy;

    public ProxyDefinition(Proxy proxy) {
        this.proxy = proxy;
    }

    public Proxy getProxy() {
        return proxy;
    }

    public void setProxy(Proxy proxy) {
        this.proxy = proxy;
    }
}
