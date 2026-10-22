package com.soulbridge.app.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public class RefreshTokenRequest {

    @NotBlank(message = "Refresh token không được để trống")
    @Schema(
            example = "eyJhbGciOiJIUzI1NiJ9...",
            description = "Refresh token được cấp khi đăng nhập"
    )
    private String refreshToken;

    public RefreshTokenRequest() {
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}