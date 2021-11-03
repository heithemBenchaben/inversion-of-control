package com.hch.ioc.core.workers.impl;

import com.hch.ioc.core.enums.Scope;
import com.hch.ioc.core.processors.context.BeanProcessContext;
import com.hch.ioc.core.processors.template.ProcessorsTemplateProvider;
import com.hch.ioc.core.registries.BeanDefinitionRegistry;
import com.hch.ioc.core.workers.Worker;

public class BeanWorker implements Worker {

    @Override
    public void start() {
        // loop over definition registry and start creation lifecycle for each definition
        BeanDefinitionRegistry
                .getInstance()
                .getRegistry()
                .entrySet()
                .forEach(keyValue -> {
                            // beanProcessContext will hold the definition and the created instance
                            start(new BeanProcessContext(keyValue.getValue()));
                        }
                );
    }

    private BeanProcessContext start(BeanProcessContext beanProcessContext) {
        // resolve only Singleton for this step
        if (beanProcessContext.getIocScanDefinition().getScope() == Scope.SINGLETON) {
            // loop over all processors and execute process in order to update beanProcessContext
            ProcessorsTemplateProvider.process(beanProcessContext);
        }
        return beanProcessContext;
    }

}