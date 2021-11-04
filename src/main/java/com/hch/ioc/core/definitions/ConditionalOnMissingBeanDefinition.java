package com.hch.ioc.core.definitions;

import com.hch.ioc.core.annotations.ConditionalOnMissingBean;

import java.lang.annotation.Annotation;

public class ConditionalOnMissingBeanDefinition {

    private ConditionalOnMissingBean conditionalMissingBean;

    public ConditionalOnMissingBeanDefinition(ConditionalOnMissingBean conditionalMissingBean) {
        this.conditionalMissingBean = conditionalMissingBean;
    }

    public ConditionalOnMissingBean getConditionalMissingBean() {
        return conditionalMissingBean;
    }

    public void setConditionalMissingBean(ConditionalOnMissingBean conditionalMissingBean) {
        this.conditionalMissingBean = conditionalMissingBean;
    }
}
