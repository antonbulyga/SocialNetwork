package com.senla.exception;

public class SQLErrors extends RuntimeException {
    public SQLErrors(String msg, Throwable t) {
        super(msg, t);
    }

    public SQLErrors(String msg) {
        super(msg);
    }
}
