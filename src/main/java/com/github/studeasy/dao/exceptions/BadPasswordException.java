package com.github.studeasy.dao.exceptions;

/**
 * Exception occuring when a user tries to connect, but
 * doesn't have the right credentials
 */
public class BadPasswordException extends Exception {

    /**
     * Create the exception with a message to display
     * @param message, the message to display
     */
    public BadPasswordException(String message) {
        super(message);
    }
}
