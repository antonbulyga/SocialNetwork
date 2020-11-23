package com.senla.model.exception;

public class IncorrectRequest extends RuntimeException {
    public IncorrectRequest(String msg, Throwable t) {
        super(msg, t);
    }

    public IncorrectRequest(String msg) {
        super(msg);
    }
}
