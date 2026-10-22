package com.soulbridge.app.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record LoginUserRequest(

        @NotBlank(
                message = "Username hoặc email không được để trống"
        )
        @Schema(
                example = "abc",
                description = "Username hoặc email của người dùng"
        )
        String username,

        @NotBlank(
                message = "Password không được để trống"
        )
        @Schema(
                example = "Abc@123456",
                description = "Mật khẩu đăng nhập"
        )
        String password
) {
}