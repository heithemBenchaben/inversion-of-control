package com.hch.ioc.core.registries;

import com.hch.ioc.core.definitions.IocScanDefinition;
import com.hch.ioc.core.processors.bean.context.BeanProcessContext;
import com.hch.ioc.core.processors.bean.template.ProcessorsTemplateProvider;
import com.hch.ioc.core.utils.ContainerUtils;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BeanRegistry {

    private static BeanRegistry beanRegistry;

    private Map<String, Object> beans;

    private List<BeanProcessContext> beforeDestroyBeans;

    private BeanRegistry() {
        beans = new LinkedHashMap<>();
        beforeDestroyBeans = new LinkedList<>();
    }

    public static BeanRegistry getInstance() {
        if (beanRegistry == null) {
            beanRegistry = new BeanRegistry();
        }
        return beanRegistry;
    }

    public Object getBean(String name) {
        return ((beans.get(name) != null) ? this.beans.get(name) : createProtypeInstance(name));
    }

    public Object getBean(Class<?> clazz) {
        String name = clazz.getName();
        return getBean(name);
    }

    private Object createProtypeInstance(String beanName) {
        BeanProcessContext beanProcessContext = new BeanProcessContext(getIocScanDefinitionByName(beanName));
        ProcessorsTemplateProvider.process(beanProcessContext);
        return beanProcessContext.getObject();
    }

    private IocScanDefinition getIocScanDefinitionByName(String name) {
        IocScanDefinition iocSD = BeanDefinitionRegistry.getInstance().getRegistry().get(name);
        return (iocSD != null ? iocSD : ContainerUtils.findIocScanDefinitionByType(name));
    }

    public Map<String, Object> getBeans() {
        return beans;
    }

    public void setBeans(Map<String, Object> beans) {
        this.beans = beans;
    }

    public List<BeanProcessContext> getBeforeDestroyBeans() {
        return beforeDestroyBeans;
    }

    public void setBeforeDestroyBeans(List<BeanProcessContext> beforeDestroyBeans) {
        this.beforeDestroyBeans = beforeDestroyBeans;
    }
}
