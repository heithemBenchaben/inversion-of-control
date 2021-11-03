package com.hch.ioc.core.annotations;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IocProfile {
    String[] profiles() default {};

}
