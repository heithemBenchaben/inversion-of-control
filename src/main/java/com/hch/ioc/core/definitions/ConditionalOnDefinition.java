package com.hch.ioc.core.definitions;

import java.lang.annotation.Annotation;

public class ConditionalOnDefinition {

    private Annotation conditionalOn;

    public ConditionalOnDefinition(Annotation conditionalOn) {
        this.conditionalOn = conditionalOn;
    }

    public Annotation getConditionalOn() {
        return conditionalOn;
    }

    public void setConditionalOn(Annotation conditionalOn) {
        this.conditionalOn = conditionalOn;
    }
}
