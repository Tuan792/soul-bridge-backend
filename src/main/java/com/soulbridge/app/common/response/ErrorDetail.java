package com.soulbridge.app.common.response;

public record ErrorDetail(
        String field,
        String issue
) {
}
