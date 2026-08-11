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

    // Same pattern as handleNotFound above, for the other entity that can be "not found".
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFound(UserNotFoundException ex) {

        ApiError body = new ApiError(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                Instant.now());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    // 409, not 404/400: the request is well-formed and the resource being
    // referenced (the email) is fine on its own -- the problem is it CONFLICTS
    // with a resource that already exists. That's exactly what 409 means.
    @ExceptionHandler(EmailAlreadyInUseException.class)
    public ResponseEntity<ApiError> handleEmailAlreadyInUse(EmailAlreadyInUseException ex) {

        ApiError body = new ApiError(
                HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                Instant.now());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    // 401: the request needed proof of identity and didn't provide valid proof.
    // Same generic message whether the email didn't exist or the password was
    // wrong -- see InvalidCredentialsException for why that's deliberate.
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiError> handleInvalidCredentials(InvalidCredentialsException ex) {

        ApiError body = new ApiError(
                HttpStatus.UNAUTHORIZED.value(),
                ex.getMessage(),
                Instant.now());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
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