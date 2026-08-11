package com.sicredi.todo.exception;

// Deliberately generic message -- used for BOTH "no such email" and "wrong
// password" so a client can never tell which one failed. Distinguishing them
// would let an attacker enumerate which emails are registered.
public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException() {
        super("Invalid email or password");
    }
}
