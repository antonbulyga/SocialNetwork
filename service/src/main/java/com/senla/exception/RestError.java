package com.senla.exception;

public class RestError extends RuntimeException{
    public RestError() {
    }

    public RestError(String message) {
        super(message);
    }

    public RestError(String message, Throwable cause) {
        super(message, cause);
    }

    public RestError(Throwable cause) {
        super(cause);
    }

    public RestError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
