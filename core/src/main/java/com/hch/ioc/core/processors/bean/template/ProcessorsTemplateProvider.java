package com.hch.ioc.core.processors.bean.template;

import com.hch.ioc.core.processors.bean.BeanProcessor;
import com.hch.ioc.core.processors.bean.context.BeanProcessContext;
import com.hch.ioc.core.processors.bean.impl.*;

import java.util.Arrays;
import java.util.List;

public class ProcessorsTemplateProvider {

    private static List<BeanProcessor> beanProcessors;

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
        if (beanProcessors == null) {
            beanProcessors = Arrays.asList(
                    // create empty instance
                    new BeanInitializerBeanProcessor(),
                    // set external properties
                    new ExternalPropertiesBeanProcessor(),
                    // set instance's dependencies
                    new DependencySetterBeanProcessor(),
                    // invock method annotated with afterPropertiesSet if exist
                    new AfterPropertiesSetBeanProcessor(),
                    new ProxyBeanProcessor(),
                    // add the instance to the list beforeDestroy if there is method annotated by BeforeDestroy
                    new BeforeDestroyBeanProcessor(),
                    // add the instance to the bean registry for singleton scope
                    // for prototype scope the instance will be created at runtime when we call getBean method
                    new ReadyToUseBeanBeanProcessor()
            );
        }
        return beanProcessors;
    }
}
