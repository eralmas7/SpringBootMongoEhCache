package com.assignment.media.exception;

/**
 * Exception throws when there are issues around initialising the media application.
 */
public class InitializationException extends RuntimeException {

    private static final long serialVersionUID = -1095482579065582014L;

    /**
     * Exception when we face any issue around initialising media application.
     * 
     * @param message
     * @param throwable
     */
    public InitializationException(final String message, final Throwable throwable) {
        super(message, throwable);
    }
}
