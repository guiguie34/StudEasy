package com.github.studeasy.logic.facades.exceptions;

public class ErrorBuyCoupon extends Exception{

    /**
     * Create the exception with a message to display
     * @param message the message to display
     */
    public ErrorBuyCoupon(String message) {
        super(message);
    }
}
