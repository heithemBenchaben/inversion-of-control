package com.hch.ioc.test.beans.impl;

import com.hch.ioc.core.annotations.*;
import com.hch.ioc.test.beans.I1;
import com.hch.ioc.test.beans.I4;

@IocProfile(profiles = {"test"})
@IocScan
public class A implements I1 {
    @IocInject
    private I4 i4;

    @ExternalProperty("project.name")
    private String projectName;

    @AfterPropertiesSet
    public void afterPropertiesSet() {
        System.out.println(String.format("afterPropertiesSet :: A :: %s", projectName));
    }

    @BeforeDestroy
    public void beforeDestroy() {
        System.out.println("beforeDestroy :: A");
    }

    public I4 getI4() {
        return i4;
    }

    public void setI4(I4 i4) {
        this.i4 = i4;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
