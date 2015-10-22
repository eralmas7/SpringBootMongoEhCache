package com.assignment.media.exception;

/**
 * This exception is thrown when the requested chat entry is not found.
 * 
 */
public class ChatNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 6410866039708844675L;

    public ChatNotFoundException(final String id) {
        super(String.format("No chat entry found with id: <%s>", id));
    }
}
