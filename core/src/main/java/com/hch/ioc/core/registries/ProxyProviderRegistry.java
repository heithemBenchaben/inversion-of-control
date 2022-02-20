package com.hch.ioc.core.registries;

import com.hch.ioc.core.proxies.ProxyProvider;

import java.util.HashMap;
import java.util.Map;

public class ProxyProviderRegistry {

    private Map<String, ProxyProvider> proxyProviderMap;

    public ProxyProviderRegistry() {
        proxyProviderMap=new HashMap<>();
    }

    public Map<String, ProxyProvider> getProxyProviderMap() {
        return proxyProviderMap;
    }

    public void setProxyProviderMap(Map<String, ProxyProvider> proxyProviderMap) {
        this.proxyProviderMap = proxyProviderMap;
    }
}
