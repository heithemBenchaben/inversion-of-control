package com.hch.ioc.core.definitions;

import com.hch.ioc.core.enums.Scope;

import java.util.LinkedList;
import java.util.List;

public class IocScanDefinition {

    private Class<?> clazz;
    private List<Class<?>> types;
    private List<String> profiles;
    private Scope scope;
    private Boolean isProxied;
    private List<IocInjectDefinition> iocInjectDefinitions;
    private List<ExternalPropertyDefinition> externalPropertyDefinitions;
    private AfterPropertiesSetDefinition afterPropertiesSetDefinition;
    private BeforeDestroyDefinition beforeDestroyDefinition;
    private ConditionalOnMissingBeanDefinition conditionalOnMissingBeanDefinition;
    private ConditionalOnDefinition conditionalOnDefinition;
    private ProxyDefinition proxyDefinition;

    public IocScanDefinition(Class<?> clazz) {
        this.clazz = clazz;
        this.types = new LinkedList<>();
        types.add(clazz);
        this.isProxied = Boolean.FALSE;
        this.profiles = new LinkedList<>();
        this.iocInjectDefinitions = new LinkedList<>();
        this.externalPropertyDefinitions = new LinkedList<>();
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public List<Class<?>> getTypes() {
        return types;
    }

    public void setTypes(List<Class<?>> types) {
        this.types = types;
    }

    public List<IocInjectDefinition> getIocInjectDefinitions() {
        return iocInjectDefinitions;
    }

    public void setIocInjectDefinitions(List<IocInjectDefinition> iocInjectDefinitions) {
        this.iocInjectDefinitions = iocInjectDefinitions;
    }

    public List<ExternalPropertyDefinition> getExternalPropertyDefinitions() {
        return externalPropertyDefinitions;
    }

    public void setExternalPropertyDefinitions(List<ExternalPropertyDefinition> externalPropertyDefinitions) {
        this.externalPropertyDefinitions = externalPropertyDefinitions;
    }

    public AfterPropertiesSetDefinition getAfterPropertiesSetDefinition() {
        return afterPropertiesSetDefinition;
    }

    public void setAfterPropertiesSetDefinition(AfterPropertiesSetDefinition afterPropertiesSetDefinition) {
        this.afterPropertiesSetDefinition = afterPropertiesSetDefinition;
    }

    public BeforeDestroyDefinition getBeforeDestroyDefinition() {
        return beforeDestroyDefinition;
    }

    public void setBeforeDestroyDefinition(BeforeDestroyDefinition beforeDestroyDefinition) {
        this.beforeDestroyDefinition = beforeDestroyDefinition;
    }

    public List<String> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<String> profiles) {
        this.profiles = profiles;
    }

    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public ConditionalOnMissingBeanDefinition getConditionalOnMissingBeanDefinition() {
        return conditionalOnMissingBeanDefinition;
    }

    public void setConditionalOnMissingBeanDefinition(ConditionalOnMissingBeanDefinition conditionalOnMissingBeanDefinition) {
        this.conditionalOnMissingBeanDefinition = conditionalOnMissingBeanDefinition;
    }

    public ConditionalOnDefinition getConditionalOnDefinition() {
        return conditionalOnDefinition;
    }

    public void setConditionalOnDefinition(ConditionalOnDefinition conditionalOnDefinition) {
        this.conditionalOnDefinition = conditionalOnDefinition;
    }

    public ProxyDefinition getProxyDefinition() {
        return proxyDefinition;
    }

    public void setProxyDefinition(ProxyDefinition proxyDefinition) {
        this.proxyDefinition = proxyDefinition;
    }

    public Boolean getProxied() {
        return isProxied;
    }

    public void setProxied(Boolean proxied) {
        isProxied = proxied;
    }
}
