package com.soulbridge.app.common.exception;

import com.soulbridge.app.common.response.ErrorDetail;

import java.util.List;

public class ResourceConflictException extends RuntimeException {

    private final List<ErrorDetail> details;

    public ResourceConflictException(String message, List<ErrorDetail> details) {
        super(message);
        this.details = List.copyOf(details);
    }

    public List<ErrorDetail> getDetails() {
        return details;
    }
}
