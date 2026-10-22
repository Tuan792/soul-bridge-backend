package com.soulbridge.app.user.dto.response;

public record RefreshTokenResponse(
        String accessToken,
        long expiresIn
) {
}