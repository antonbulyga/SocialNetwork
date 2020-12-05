package com.senla.exception;

import io.jsonwebtoken.JwtException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler{

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

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException e) {
        RestApiResponse restApiResponse = new RestApiResponse(HttpStatus.FORBIDDEN, "Access denied");
        return new ResponseEntity<>(restApiResponse, HttpStatus.FORBIDDEN);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        List<String> errorList = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        return response(ex, request, headers, HttpStatus.BAD_REQUEST, errorList);
    }

    private ResponseEntity<Object> response(Exception ex, WebRequest request, HttpHeaders headers, HttpStatus status,
                                            List<String> messages) {
        return handleExceptionInternal(ex, messages, headers, status, request);
    }


}
