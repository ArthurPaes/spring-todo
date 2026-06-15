package com.sicredi.todo.exception;

// extends RuntimeException = "unchecked" -> no one is forced to try/catch it.
// It just bubbles up until our global handler catches it. Behaves like a normal JS throw.
public class TodoNotFoundException extends RuntimeException {

    public TodoNotFoundException(Long id) {
        // super(...) sets the exception's message. We build a meaningful one from the id.
        super("Todo not found with id: " + id);
    }
}
