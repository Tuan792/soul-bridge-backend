package com.soulbridge.app.common.response;

import java.util.List;

public record ErrorResponse(
        boolean success,
        String message,
        List<ErrorDetail> details
) {

    public static ErrorResponse failure(String message, List<ErrorDetail> details) {
        return new ErrorResponse(false, message, details);
    }
}
