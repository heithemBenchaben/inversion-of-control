package com.hch.ioc.core.processors.bean.template;

import com.hch.ioc.core.processors.bean.BeanProcessor;
import com.hch.ioc.core.processors.bean.context.BeanProcessContext;
import com.hch.ioc.core.processors.bean.impl.*;

import java.util.Arrays;
import java.util.List;

public class ProcessorsTemplateProvider {

    /**
     * loop over beanProcessors and execute process in order to apply all processor behaviour into beanProcessContext
     *
     * @param beanProcessContext
     */
    public static void process(BeanProcessContext beanProcessContext) {
        getBeanProcessors()
                .forEach(beanProcessor ->
                        beanProcessor.process(beanProcessContext)
                );
    }

    /**
     * build beanProcessors template
     * each processor has a role in the instance creation lifecycle
     *
     * @return List<BeanProcessor>
     */
    public static List<BeanProcessor> getBeanProcessors() {
        return Arrays.asList(
                // create empty instance
                BeanInitializerBeanProcessor.getInstance(),
                // set external properties
                ExternalPropertiesBeanProcessor.getInstance(),
                // set instance's dependencies
                DependencySetterBeanProcessor.getInstance(),
                // invock method annotated with afterPropertiesSet if exist
                AfterPropertiesSetBeanProcessor.getInstance(),
                // try create a proxy if needed
                ProxyBeanProcessor.getInstance(),
                // add the instance to the list beforeDestroy if there is method annotated by BeforeDestroy
                BeforeDestroyBeanProcessor.getInstance(),
                // add the instance to the bean registry for singleton scope
                // for prototype scope the instance will be created at runtime when we call getBean method
                ReadyToUseBeanBeanProcessor.getInstance()
        );
    }
}
