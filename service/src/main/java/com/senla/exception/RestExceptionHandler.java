package com.senla.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException e) {
        RestApiResponse restApiResponse = new RestApiResponse(HttpStatus.NOT_FOUND, e.getMessage());
        return new ResponseEntity<>(restApiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RestError.class)
    protected ResponseEntity<Object> handleRestError(RestError e) {
        RestApiResponse restApiResponse = new RestApiResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        return new ResponseEntity<>(restApiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IncorrectRequest.class)
    protected ResponseEntity<Object> handleIncorrectRequest(IncorrectRequest e) {
        RestApiResponse restApiResponse = new RestApiResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        return new ResponseEntity<>(restApiResponse, HttpStatus.BAD_REQUEST);
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

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleValidationException(ConstraintViolationException e) {
        RestApiResponse restApiResponse = new RestApiResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        return new ResponseEntity<>(restApiResponse, HttpStatus.BAD_REQUEST);
    }
}
