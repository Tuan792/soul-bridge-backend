package com.soulbridge.app.user.service;

import com.soulbridge.app.user.dto.request.LoginUserRequest;
import com.soulbridge.app.user.dto.request.RefreshTokenRequest;
import com.soulbridge.app.user.dto.request.RegisterUserRequest;
import com.soulbridge.app.user.dto.response.LoginUserResponse;
import com.soulbridge.app.user.dto.response.RefreshTokenResponse;
import com.soulbridge.app.user.dto.response.RegisterUserResponse;

public interface UserService {

    RegisterUserResponse register(
            RegisterUserRequest request
    );

    LoginUserResponse login(
            LoginUserRequest request
    );

    RefreshTokenResponse refreshToken(
            RefreshTokenRequest request
    );
}