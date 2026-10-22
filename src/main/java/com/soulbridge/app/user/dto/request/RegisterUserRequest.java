package com.soulbridge.app.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record RegisterUserRequest(

        @NotBlank(message = "Username không được để trống")
        @Schema(
                example = "abc",
                description = "Tên đăng nhập của người dùng"
        )
        String username,

        @NotBlank(message = "Email không được để trống")
        @Email(message = "Email không đúng định dạng")
        @Schema(
                example = "example@gmail.com",
                description = "Email của người dùng"
        )
        String email,

        @NotBlank(message = "Password không được để trống")
        @Schema(
                example = "Abc@123456",
                description = "Mật khẩu phải có chữ hoa, số và ký tự đặc biệt"
        )
        String password,

        @NotBlank(message = "Consent version không được để trống")
        @Pattern(
                regexp = "v1\\.0|v2\\.0",
                message = "Consent version chỉ được là v1.0 hoặc v2.0"
        )
        @Schema(
                example = "v1.0",
                allowableValues = {"v1.0", "v2.0"},
                defaultValue = "v1.0",
                description = "Phiên bản điều khoản người dùng chấp thuận"
        )
        String consentVersion
) {
}