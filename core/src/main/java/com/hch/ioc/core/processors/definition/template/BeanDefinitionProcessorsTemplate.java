package com.hch.ioc.core.processors.definition.template;

import com.hch.ioc.core.processors.definition.BeanDefinitionProcessor;
import com.hch.ioc.core.processors.definition.impl.ConditionalOnCleanUpProcessor;
import com.hch.ioc.core.processors.definition.impl.ConditionalOnMissingBeanProcessor;
import com.hch.ioc.core.processors.definition.impl.ParentChildProcessor;
import com.hch.ioc.core.processors.definition.impl.ProfileProcessor;

import java.util.Arrays;
import java.util.List;

public class BeanDefinitionProcessorsTemplate {

    private static List<BeanDefinitionProcessor> beanDefinitionProcessors;

    public static void process() {
        getBeanDefinitionProcessors().forEach(beanDefinitionProcessor -> beanDefinitionProcessor.process());
    }

    public static List<BeanDefinitionProcessor> getBeanDefinitionProcessors() {
        if (beanDefinitionProcessors == null) {
            beanDefinitionProcessors = Arrays.asList(
                    new ConditionalOnCleanUpProcessor(),
                    new ConditionalOnMissingBeanProcessor(),
                    new ProfileProcessor(),
                    new ParentChildProcessor()
            );
        }
        return beanDefinitionProcessors;
    }
}
