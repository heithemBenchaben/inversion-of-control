package com.hch.ioc.core.processors;

import com.hch.ioc.core.processors.context.BeanProcessContext;

public interface BeanProcessor {

    void process(BeanProcessContext beanProcessContext);
}
