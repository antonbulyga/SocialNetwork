package com.senla.model.exception;

public class RestError extends RuntimeException {
    public RestError(String msg, Throwable t) {
        super(msg, t);
    }

    public RestError(String msg) {
        super(msg);
    }
}
