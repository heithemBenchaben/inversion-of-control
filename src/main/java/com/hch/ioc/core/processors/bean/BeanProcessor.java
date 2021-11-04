package com.hch.ioc.core.processors.bean;

import com.hch.ioc.core.processors.bean.context.BeanProcessContext;

public interface BeanProcessor {

    void process(BeanProcessContext beanProcessContext);
}
