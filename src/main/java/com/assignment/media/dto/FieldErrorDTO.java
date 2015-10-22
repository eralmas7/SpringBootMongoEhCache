package com.assignment.media.dto;

/**
 * Any field validator error could be encapsulated and could be passed via this DTO.
 */
public final class FieldErrorDTO {

    private final String field;
    private final String message;

    public FieldErrorDTO(final String field, final String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }
}
