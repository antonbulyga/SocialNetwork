package com.senla.exception;

public class SQLErrors extends RuntimeException {
    public SQLErrors() {
    }

    public SQLErrors(String message) {
        super(message);
    }

    public SQLErrors(String message, Throwable cause) {
        super(message, cause);
    }

    public SQLErrors(Throwable cause) {
        super(cause);
    }

    public SQLErrors(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
