package com.soulbridge.app.user.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soulbridge.app.common.exception.ResourceConflictException;
import com.soulbridge.app.common.exception.UnauthorizedException;
import com.soulbridge.app.common.response.ErrorDetail;
import com.soulbridge.app.security.jwt.JwtService;
import com.soulbridge.app.user.dto.request.LoginUserRequest;
import com.soulbridge.app.user.dto.request.RefreshTokenRequest;
import com.soulbridge.app.user.dto.request.RegisterUserRequest;
import com.soulbridge.app.user.dto.response.LoginUserResponse;
import com.soulbridge.app.user.dto.response.RefreshTokenResponse;
import com.soulbridge.app.user.dto.response.RegisterUserResponse;
import com.soulbridge.app.user.entity.User;
import com.soulbridge.app.user.mapper.UserMapper;
import com.soulbridge.app.user.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserServiceImpl(
            UserRepository userRepository,
            UserMapper userMapper,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    @Transactional
    public RegisterUserResponse register(
            RegisterUserRequest request
    ) {

        List<ErrorDetail> details = new ArrayList<>();

        String normalizedUsername =
                request.username().trim();

        String normalizedEmail =
                request.email()
                        .trim()
                        .toLowerCase();

        if (userRepository.existsByUsername(
                normalizedUsername
        )) {
            details.add(
                    new ErrorDetail(
                            "username",
                            "Tên người dùng đã tồn tại"
                    )
            );
        }

        if (userRepository.existsByEmail(
                normalizedEmail
        )) {
            details.add(
                    new ErrorDetail(
                            "email",
                            "Email đã tồn tại trên hệ thống"
                    )
            );
        }

        if (!details.isEmpty()) {
            throw new ResourceConflictException(
                    "Dữ liệu đã tồn tại",
                    details
            );
        }

        String passwordHash =
                passwordEncoder.encode(
                        request.password()
                );

        User user = userMapper.toEntity(
                request,
                passwordHash,
                normalizedEmail
        );

        user.setConsentVersion(
                request.consentVersion()
        );

        User savedUser =
                userRepository.save(user);

        return userMapper.toRegisterResponse(
                savedUser
        );
    }

    @Override
    public LoginUserResponse login(
            LoginUserRequest request
    ) {

        String identifier =
                request.username().trim();

        User user = userRepository
                .findByUsername(identifier)
                .or(() ->
                        userRepository.findByEmail(
                                identifier.toLowerCase()
                        )
                )
                .orElseThrow(() ->
                        new UnauthorizedException(
                                "Sai tên đăng nhập hoặc mật khẩu"
                        )
                );

        if (!Boolean.TRUE.equals(
                user.getActive()
        )) {
            throw new UnauthorizedException(
                    "Tài khoản đã bị khóa hoặc không còn hoạt động"
            );
        }

        boolean passwordMatches =
                passwordEncoder.matches(
                        request.password(),
                        user.getPassword()
                );

        if (!passwordMatches) {
            throw new UnauthorizedException(
                    "Sai tên đăng nhập hoặc mật khẩu"
            );
        }

        String accessToken =
                jwtService.generateAccessToken(user);

        String refreshToken =
                jwtService.generateRefreshToken(user);

        return new LoginUserResponse(
                user.getUsername(),
                user.getEmail(),
                accessToken,
                refreshToken,
                jwtService.getAccessTokenExpiration() / 1000
        );
    }

    @Override
    public RefreshTokenResponse refreshToken(
            RefreshTokenRequest request
    ) {

        String refreshToken =
                request.getRefreshToken();

        if (!jwtService.isTokenValid(refreshToken)) {
            throw new UnauthorizedException(
                    "Refresh token không hợp lệ hoặc đã hết hạn"
            );
        }

        String tokenType =
                jwtService.extractTokenType(refreshToken);

        if (!"refresh".equals(tokenType)) {
            throw new UnauthorizedException(
                    "Token không phải refresh token"
            );
        }

        String username =
                jwtService.extractUsername(refreshToken);

        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new UnauthorizedException(
                                "Tài khoản không tồn tại"
                        )
                );

        if (!Boolean.TRUE.equals(
                user.getActive()
        )) {
            throw new UnauthorizedException(
                    "Tài khoản đã bị khóa hoặc không còn hoạt động"
            );
        }

        String accessToken =
                jwtService.generateAccessToken(user);

        return new RefreshTokenResponse(
                accessToken,
                jwtService.getAccessTokenExpiration() / 1000
        );
    }
}