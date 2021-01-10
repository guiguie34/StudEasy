package com.github.studeasy.logic.facades.exceptions;

public class ErrorCommand extends Exception{

    /**
     * Create the exception with a message to display
     * @param message the message to display
     */
    public ErrorCommand(String message) {
        super(message);
    }
}
