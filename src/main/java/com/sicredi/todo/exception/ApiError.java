package com.sicredi.todo.exception;

import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

// A plain DTO. Its ONLY job is to give every error response the same JSON shape.
// @JsonInclude(NON_NULL): when 'errors' is null (e.g. on a 404), Jackson omits the key
// entirely instead of printing "errors": null. Keeps non-validation errors clean.
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {

    private int status;        // e.g. 404
    private String message;    // e.g. "not found with id: 99999"
    private Instant timestamp; // when it happened (Instant = a point in time, serializes to ISO-8601)
    private List<String> errors; // field-level messages, e.g. ["title: must not be empty"]. Null for non-validation errors.

    // Existing constructor — used by the 404 handler. Delegates to the 4-arg one with errors = null.
    public ApiError(int status, String message, Instant timestamp) {
        this(status, message, timestamp, null);
    }

    // Used by the validation handler — carries the list of field errors.
    public ApiError(int status, String message, Instant timestamp, List<String> errors) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
        this.errors = errors;
    }

    // Getters are REQUIRED: Jackson reads them to build the JSON. No getters = empty {} response.
    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public List<String> getErrors() {
        return errors;
    }
}
