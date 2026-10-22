package com.soulbridge.app.user.mapper;

import org.springframework.stereotype.Component;

import com.soulbridge.app.user.dto.request.RegisterUserRequest;
import com.soulbridge.app.user.dto.response.RegisterUserResponse;
import com.soulbridge.app.user.entity.User;

@Component
public class UserMapper {

    public User toEntity(
            RegisterUserRequest request,
            String passwordHash,
            String normalizedEmail
    ) {
        return new User(
                request.username().trim(),
                normalizedEmail,
                passwordHash
        );
    }

    public RegisterUserResponse toRegisterResponse(User user) {
        return new RegisterUserResponse(
                user.getUsername(),
                user.getEmail()
        );
    }
}