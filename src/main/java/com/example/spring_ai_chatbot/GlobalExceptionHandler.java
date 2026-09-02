package com.example.spring_ai_chatbot;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleValidationException(
            MethodArgumentNotValidException exception) {

        Map<String, Object> error = new HashMap<>();

        error.put("status", 400);
        error.put("message", exception.getBindingResult()
                .getFieldError()
                .getDefaultMessage());

        return error;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleConstraintViolationException(
            ConstraintViolationException exception) {

        Map<String, Object> error = new HashMap<>();

        error.put("status", 400);
        error.put("message", exception.getMessage());

        return error;
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> handleUserNotFoundException(
            UserNotFoundException exception) {

        Map<String, Object> error = new HashMap<>();

        error.put("status", 404);
        error.put("message", exception.getMessage());

        return error;
    }
}