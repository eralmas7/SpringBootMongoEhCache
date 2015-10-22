package com.assignment.media.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Validation errors data transfer object.
 */
public final class ValidationErrorDTO {

    private final List<FieldErrorDTO> fieldErrors = new ArrayList<>();

    public void addFieldError(final String path, final String message) {
        final FieldErrorDTO error = new FieldErrorDTO(path, message);
        fieldErrors.add(error);
    }

    public List<FieldErrorDTO> getFieldErrors() {
        return Collections.unmodifiableList(fieldErrors);
    }
}
