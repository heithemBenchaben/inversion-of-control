package com.hch.ioc.core.definitions;

import java.lang.annotation.Annotation;

public class ConditionalOnMissingBeanDefinition {

    private Annotation onConditionalMissingBean;

    public ConditionalOnMissingBeanDefinition(Annotation onConditionalMissingBean) {
        this.onConditionalMissingBean = onConditionalMissingBean;
    }

    public Annotation getOnConditionalMissingBean() {
        return onConditionalMissingBean;
    }

    public void setOnConditionalMissingBean(Annotation onConditionalMissingBean) {
        this.onConditionalMissingBean = onConditionalMissingBean;
    }
}
