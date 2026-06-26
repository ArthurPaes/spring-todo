package com.sicredi.todo.exception;

import java.time.Instant;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    // @Valid throws MethodArgumentNotValidException when a @RequestBody fails validation.
    // Spring catches it and routes it here -> a clean 400 instead of the default leaky blob.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex) {

        // getBindingResult() holds the validation outcome;
        // getFieldErrors() = one entry per field that failed.
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .toList();

        ApiError body = new ApiError(
                HttpStatus.BAD_REQUEST.value(), // 400
                "Validation failed",            // generic summary; specifics live in 'errors'
                Instant.now(),
                errors);

        return ResponseEntity.badRequest().body(body); // badRequest() == status(400)
    }
}