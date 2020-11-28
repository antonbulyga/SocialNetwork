package com.senla.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected String handleEntityNotFoundException(EntityNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(RestError.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected String handleRestError(RestError e) {
        return e.getMessage();
    }

    @ExceptionHandler(IncorrectRequest.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected String handleIncorrectRequest(IncorrectRequest e) {
        return e.getMessage();
    }


}
