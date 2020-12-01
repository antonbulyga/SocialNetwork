package com.senla.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

public class IncorrectRequest extends RuntimeException {
    public IncorrectRequest() {
    }

    public IncorrectRequest(String message) {
        super(message);
    }

    public IncorrectRequest(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectRequest(Throwable cause) {
        super(cause);
    }

    public IncorrectRequest(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public IncorrectRequest(HttpStatus badRequest, String localizedMessage, List<String> errorList) {
    }
}
