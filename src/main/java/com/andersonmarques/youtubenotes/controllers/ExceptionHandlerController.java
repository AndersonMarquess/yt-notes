package com.andersonmarques.youtubenotes.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.andersonmarques.youtubenotes.models.ApiError;
import com.fasterxml.jackson.databind.JsonMappingException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler({ IllegalArgumentException.class, DataIntegrityViolationException.class,
            JsonMappingException.class, MethodArgumentNotValidException.class })
    public ResponseEntity<ApiError> invalidArgument(Exception ex, HttpServletRequest req) {
        List<String> messages = extractMessageFromException(ex);
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, messages, req.getRequestURI());
        return ResponseEntity.status(apiError.getStatus()).body(apiError);
    }

    private List<String> extractMessageFromException(Exception exception) {
        if (exception instanceof DataIntegrityViolationException) {
            return Arrays.asList("Data integrity violation - verify your payload and try again");
        } else if (exception instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException ex = (MethodArgumentNotValidException) exception;
            return ex.getBindingResult().getFieldErrors().stream().map(err -> err.getDefaultMessage())
                    .collect(Collectors.toList());
        } else {
            return Arrays.asList(exception.getMessage());
        }
    }
}
