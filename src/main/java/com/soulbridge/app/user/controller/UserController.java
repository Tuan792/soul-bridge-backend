package com.soulbridge.app.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soulbridge.app.common.response.ApiResponse;
import com.soulbridge.app.user.dto.request.LoginUserRequest;
import com.soulbridge.app.user.dto.request.RefreshTokenRequest;
import com.soulbridge.app.user.dto.request.RegisterUserRequest;
import com.soulbridge.app.user.dto.response.LoginUserResponse;
import com.soulbridge.app.user.dto.response.RefreshTokenResponse;
import com.soulbridge.app.user.dto.response.RegisterUserResponse;
import com.soulbridge.app.user.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
@Tag(
        name = "User",
        description = "API đăng ký và đăng nhập người dùng"
)
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @Operation(
            summary = "Đăng ký tài khoản",
            description = "Tạo tài khoản người dùng mới"
    )
    public ResponseEntity<ApiResponse<RegisterUserResponse>> register(
            @Valid @RequestBody RegisterUserRequest request
    ) {

        RegisterUserResponse response =
                userService.register(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        ApiResponse.<RegisterUserResponse>success(
                                "Đăng ký tài khoản thành công",
                                response
                        )
                );
    }

    @PostMapping("/sign-in")
    @Operation(
            summary = "Đăng nhập",
            description = "Đăng nhập bằng username hoặc email và password"
    )
    public ResponseEntity<ApiResponse<LoginUserResponse>> signIn(
            @Valid @RequestBody LoginUserRequest request
    ) {

        LoginUserResponse response =
                (LoginUserResponse) userService.login(request);

        return ResponseEntity
                .ok(
                        ApiResponse.<LoginUserResponse>success(
                                "Đăng nhập thành công",
                                response
                        )
                );
    }
    @PostMapping("/refresh-token")
        @Operation(
                summary = "Làm mới access token",
                description = "Sử dụng refresh token để lấy access token mới"
        )
        public ResponseEntity<ApiResponse<RefreshTokenResponse>> refreshToken(
                @Valid @RequestBody RefreshTokenRequest request
        ) {

        RefreshTokenResponse response =
                userService.refreshToken(request);

        return ResponseEntity.ok(
                ApiResponse.<RefreshTokenResponse>success(
                        "Làm mới access token thành công",
                        response
                )
        );
        }
}