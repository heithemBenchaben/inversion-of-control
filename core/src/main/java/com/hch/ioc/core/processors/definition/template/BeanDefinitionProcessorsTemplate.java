package com.hch.ioc.core.processors.definition.template;

import com.hch.ioc.core.processors.definition.BeanDefinitionProcessor;
import com.hch.ioc.core.processors.definition.impl.ConditionalOnCleanUpProcessor;
import com.hch.ioc.core.processors.definition.impl.ConditionalOnMissingBeanProcessor;
import com.hch.ioc.core.processors.definition.impl.ParentChildProcessor;
import com.hch.ioc.core.processors.definition.impl.ProfileProcessor;

import java.util.Arrays;
import java.util.List;

public class BeanDefinitionProcessorsTemplate {

    public static void process() {
        getBeanDefinitionProcessors().forEach(beanDefinitionProcessor -> beanDefinitionProcessor.process());
    }

    public static List<BeanDefinitionProcessor> getBeanDefinitionProcessors() {
        return Arrays.asList(
                ConditionalOnCleanUpProcessor.getInstance(),
                ConditionalOnMissingBeanProcessor.getInstance(),
                ProfileProcessor.getInstance(),
                ParentChildProcessor.getInstance()
        );
    }
}
