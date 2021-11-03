package com.hch.ioc.core.annotations;

import com.hch.ioc.core.enums.Scope;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IocScope {
    Scope scope() ;
}
