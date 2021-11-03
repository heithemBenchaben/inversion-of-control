package com.hch.ioc.core.definitions;

import java.lang.reflect.Field;

public class ExternalPropertyDefinition {

    private Field field;

    private String propertyName;

    public ExternalPropertyDefinition(Field field, String propertyName) {
        this.field = field;
        this.propertyName = propertyName;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }
}
