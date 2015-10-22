package com.assignment.media.exception;

/**
 * This exception is thrown when the requested blog entry is not found.
 * 
 */
public class BlogNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 6410866039708844675L;

    public BlogNotFoundException(final String id) {
        super(String.format("No blog entry found with id: <%s>", id));
    }
}
