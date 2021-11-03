package com.hch.ioc.core.processors.template;

import com.hch.ioc.core.processors.Processor;
import com.hch.ioc.core.processors.context.BeanProcessContext;
import com.hch.ioc.core.processors.impl.*;

import java.util.Arrays;
import java.util.List;

public class ProcessorsTemplateProvider {

    private static List<Processor> processors;

    /**
     * loop over processors and execute process in order to apply all processor behaviour into beanProcessContext
     *
     * @param beanProcessContext
     */
    public static void process(BeanProcessContext beanProcessContext) {
        getProcessors()
                .forEach(processor ->
                        processor.process(beanProcessContext)
                );
    }

    /**
     * build processors template
     * each processor has a role in the instance creation lifecycle
     *
     * @return List<Processor>
     */
    public static List<Processor> getProcessors() {
        if (processors == null) {
            processors = Arrays.asList(
                    // create empty instance
                    new BeanInitializerProcessor(),
                    // set external properties
                    new ExternalPropertiesProcessor(),
                    // set instance's dependencies
                    new DependencySetterProcessor(),
                    // invock method annotated with afterPropertiesSet if exist
                    new AfterPropertiesSetProcessor(),
                    // add the instance to the list beforeDestroy if there is method annotated by BeforeDestroy
                    new BeforeDestroyProcessor(),
                    // add the instance to the bean registry for singleton scope
                    // for prototype scope the instance will be created at runtime when we call getBean method
                    new ReadyToUseBeanProcessor()
            );
        }
        return processors;
    }
}
