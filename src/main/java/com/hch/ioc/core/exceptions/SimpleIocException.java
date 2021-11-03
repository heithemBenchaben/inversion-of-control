package com.hch.ioc.core.exceptions;

public class SimpleIocException extends RuntimeException {

    public SimpleIocException(Throwable e) {
        super(e);
    }

    public SimpleIocException(String message) {
        super(message);
    }
}
