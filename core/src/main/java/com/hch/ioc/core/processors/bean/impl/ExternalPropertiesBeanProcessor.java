package com.hch.ioc.core.processors.bean.impl;

import com.hch.ioc.core.definitions.ExternalPropertyDefinition;
import com.hch.ioc.core.definitions.IocScanDefinition;
import com.hch.ioc.core.exceptions.SimpleIocException;
import com.hch.ioc.core.processors.bean.BeanProcessor;
import com.hch.ioc.core.processors.bean.context.BeanProcessContext;
import com.hch.ioc.core.registries.PropertiesRegistry;

import java.lang.reflect.InvocationTargetException;

public class ExternalPropertiesBeanProcessor implements BeanProcessor {

    private static ExternalPropertiesBeanProcessor externalPropertiesBeanProcessor;

    private ExternalPropertiesBeanProcessor() {
    }

    public static ExternalPropertiesBeanProcessor getInstance() {
        if (externalPropertiesBeanProcessor == null) {
            externalPropertiesBeanProcessor = new ExternalPropertiesBeanProcessor();
        }
        return externalPropertiesBeanProcessor;
    }

    /**
     * set all field annotated by @ExternProperties
     *
     * @param beanProcessContext
     */
    @Override
    public void process(BeanProcessContext beanProcessContext) {
        resolveExternalProperties(beanProcessContext.getIocScanDefinition(), beanProcessContext.getObject());
    }

    private void resolveExternalProperties(IocScanDefinition iocScanDefinition, Object object) {
        iocScanDefinition
                .getExternalPropertyDefinitions()
                .forEach(
                        externalPropertyDefinition -> resolveExternalProperty(externalPropertyDefinition, object)
                );
    }


    private void resolveExternalProperty(ExternalPropertyDefinition externalPropertyDefinition, Object object) {
        try {
            // get the property name
            String propertyName = externalPropertyDefinition.getPropertyName();
            String propertyValue = PropertiesRegistry.getInstance().getProperties().get(propertyName);
            // set the property value in the target field
            String fieldName = externalPropertyDefinition.getField().getName();
            object.getClass().getMethod("set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1), String.class).invoke(object, propertyValue);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            throw new SimpleIocException(e);
        }
    }
}
