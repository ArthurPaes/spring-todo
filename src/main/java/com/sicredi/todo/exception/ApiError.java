package com.sicredi.todo.exception;

import java.time.Instant;

// A plain DTO. Its ONLY job is to give every error response the same JSON shape.
public class ApiError {

    private int status;        // e.g. 404
    private String message;    // e.g. "not found with id: 99999"
    private Instant timestamp; // when it happened (Instant = a point in time, serializes to ISO-8601)

    public ApiError(int status, String message, Instant timestamp) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
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
}