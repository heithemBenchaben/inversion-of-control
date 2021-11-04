package com.hch.ioc.test.beans.impl;

import com.hch.ioc.core.annotations.ExternalProperty;
import com.hch.ioc.core.annotations.IocScan;
import com.hch.ioc.core.annotations.IocScope;
import com.hch.ioc.core.enums.Scope;
import com.hch.ioc.test.beans.I6;

@IocScan
@IocScope(scope = Scope.PROTOTYPE)
public class Q implements I6 {

    @ExternalProperty("first.name")
    private String firstName;

    @ExternalProperty("last.name")
    private String lastName;

    public Q() {
    }

    @Override
    public void print() {
        System.out.println(String.format("reference :: %s, first name :: %s , lastName :: %s",this, getFirstName(), getLastName()));
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
