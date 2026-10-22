package com.soulbridge.app.user.dto.response;

public record LoginUserResponse(
        String username,
        String email,
        String accessToken,
        String refreshToken,
        long expiresIn
) {
}