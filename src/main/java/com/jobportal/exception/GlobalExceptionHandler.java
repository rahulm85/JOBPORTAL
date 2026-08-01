package com.jobportal.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Validation Errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        return errors;
    }

    // Generic Exception
    @ExceptionHandler(Exception.class)
    public Map<String, String> handleException(Exception ex) {

        Map<String, String> error = new HashMap<>();

        error.put("Status", String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        error.put("Message", ex.getMessage());

        return error;
    }

}