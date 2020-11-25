package com.senla.exception;

public class RestError extends RuntimeException {
    public RestError(String msg, Throwable t) {
        super(msg, t);
    }

    public RestError(String msg) {
        super(msg);
    }
}
