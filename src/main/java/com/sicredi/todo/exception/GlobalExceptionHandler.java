package com.sicredi.todo.exception;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// @RestControllerAdvice = "this class handles exceptions thrown by ANY controller, app-wide."
// It's your single, central error-to-HTTP translator (like one Express error middleware).
@RestControllerAdvice
public class GlobalExceptionHandler {

    // "When a TodoNotFoundException is thrown anywhere, run THIS instead of crashing."
    // Spring injects the actual exception object so we can read its message.
    @ExceptionHandler(TodoNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(TodoNotFoundException ex) {

        ApiError body = new ApiError(
                HttpStatus.NOT_FOUND.value(),  // .value() = the int 404
                ex.getMessage(),               // the message we set in the exception's constructor
                Instant.now());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body); // 404 + our clean body
    }
}