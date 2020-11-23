package com.senla.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(EntityNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected String handleEntityNotFoundException(EntityNotFoundException e){
        return e.getMessage();
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(RestError.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    protected String handleRestError(Exception e){
        return e.getMessage();
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(IncorrectRequest.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected String handleIncorrectRequest(IncorrectRequest e){
        return e.getMessage();
    }


}
