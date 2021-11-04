package com.hch.ioc.test.beans.impl;

import com.hch.ioc.core.annotations.*;
import com.hch.ioc.test.beans.I2;


@IocProfile(profiles = {"dev", "staging"})
@IocScan
@ConditionalOn(property = "check.conditional.on", having = "do_inject")
public class B implements I2 {

    @ExternalProperty("first.name")
    private String firstName;

    @ExternalProperty("last.name")
    private String lastName;

    @AfterPropertiesSet
    public void afterPropertiesSet() {
        System.out.println(String.format("afterPropertiesSet :: B:: %s :: %s", firstName, lastName));
    }

    @BeforeDestroy
    public void beforeDestroy() {
        System.out.println("beforeDestroy :: B");
    }

    @Override
    public void print() {
        System.out.println(String.format("reference :: %s, first name :: %s , lastName :: %s", this, getFirstName(), getLastName()));
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
