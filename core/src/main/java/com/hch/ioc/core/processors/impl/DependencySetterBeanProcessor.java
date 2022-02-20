package com.hch.ioc.core.processors.impl;

import com.hch.ioc.core.definitions.IocInjectDefinition;
import com.hch.ioc.core.exceptions.SimpleIocException;
import com.hch.ioc.core.processors.BeanProcessor;
import com.hch.ioc.core.processors.context.BeanProcessContext;
import com.hch.ioc.core.registries.BeanRegistry;

import java.lang.reflect.InvocationTargetException;

public class DependencySetterBeanProcessor implements BeanProcessor {

    private static DependencySetterBeanProcessor dependencySetterBeanProcessor;

    private DependencySetterBeanProcessor() {
    }

    public static DependencySetterBeanProcessor getInstance() {
        if (dependencySetterBeanProcessor == null) {
            dependencySetterBeanProcessor = new DependencySetterBeanProcessor();
        }
        return dependencySetterBeanProcessor;
    }

    /**
     * set all field annotated by @IocInject
     *
     * @param beanProcessContext
     */
    @Override
    public void process(BeanProcessContext beanProcessContext) {
        beanProcessContext
                .getIocScanDefinition()
                .getIocInjectDefinitions()
                .forEach(iocInjectDefinition ->
                        resolve(iocInjectDefinition, beanProcessContext.getObject())
                );
    }

    private void resolve(IocInjectDefinition iocInjectDefinition, Object object) {
        String fullFieldName = iocInjectDefinition.getField().getType().getName();
        Object dependencyInstance = BeanRegistry.getInstance().getBean(fullFieldName);
        setDependency(fullFieldName, object, dependencyInstance);
    }

    private void setDependency(String fullFieldName, Object object, Object dependencyInstance) {
        try {
            object.getClass().getMethod(buildSetMethod(fullFieldName), Class.forName(fullFieldName))
                    .invoke(object, dependencyInstance);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | ClassNotFoundException e) {
            throw new SimpleIocException(e);
        }
    }

    private String buildSetMethod(String fullFieldName) {
        String fieldName = fullFieldName.substring(fullFieldName.lastIndexOf(".") + 1);
        String cFieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        return "set".concat(cFieldName);

    }
}
