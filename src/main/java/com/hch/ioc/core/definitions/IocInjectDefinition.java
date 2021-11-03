package com.hch.ioc.core.definitions;

import java.lang.reflect.Field;

public class IocInjectDefinition {

    private Field field;

    public IocInjectDefinition(Field field) {
        this.field = field;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }
}
