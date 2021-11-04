package com.hch.ioc.core.definitions;

import com.hch.ioc.core.annotations.ConditionalOn;

public class ConditionalOnDefinition {

    private ConditionalOn conditionalOn;

    public ConditionalOnDefinition(ConditionalOn conditionalOn) {
        this.conditionalOn = conditionalOn;
    }

    public ConditionalOn getConditionalOn() {
        return conditionalOn;
    }

    public void setConditionalOn(ConditionalOn conditionalOn) {
        this.conditionalOn = conditionalOn;
    }
}
