package com.github.studeasy.dao.exceptions;

/**
 * Exception occuring when a user tries to connect, but
 * doesn't have the right credentials
 */
public class BadCredentialsException extends Exception {

    /**
     * Create the exception with a message to display
     * @param message, the message to display
     */
    public BadCredentialsException(String message) {
        super(message);
    }
}
