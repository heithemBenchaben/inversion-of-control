package com.hch.ioc.test.beans.impl;

import com.hch.ioc.core.annotations.ExternalProperty;
import com.hch.ioc.core.annotations.IocInject;
import com.hch.ioc.core.annotations.IocScan;
import com.hch.ioc.core.annotations.IocScope;
import com.hch.ioc.core.enums.Scope;
import com.hch.ioc.test.beans.I5;
import com.hch.ioc.test.beans.I6;

@IocScan
@IocScope(scope = Scope.PROTOTYPE)
public class P implements I5 {

    @IocInject
    private I6 i6;

    @ExternalProperty("first.name")
    private String firstName;

    @ExternalProperty("last.name")
    private String lastName;

    public P() {
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

    public I6 getI6() {
        return i6;
    }

    public void setI6(I6 i6) {
        this.i6 = i6;
    }
}
