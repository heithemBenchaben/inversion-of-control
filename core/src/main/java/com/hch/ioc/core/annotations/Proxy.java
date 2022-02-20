package com.hch.ioc.core.annotations;

import com.hch.ioc.core.enums.ProxyType;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Proxy {
    ProxyType type();
}
