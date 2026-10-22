package com.soulbridge.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        return http
                // API không sử dụng CSRF
                .csrf(csrf -> csrf.disable())

                // Không sử dụng session
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .authorizeHttpRequests(authorize -> authorize

                        // Swagger
                        .requestMatchers(
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        ).permitAll()

                        // Register
                        .requestMatchers(
                                HttpMethod.POST,
                                "/user/register"
                        ).permitAll()

                        // Sign-in
                        .requestMatchers(
                                HttpMethod.POST,
                                "/user/sign-in"
                        ).permitAll()

                        // Tạm thời cho phép các API khác
                        .anyRequest().permitAll()
                )

                // Tắt Basic Auth
                .httpBasic(httpBasic ->
                        httpBasic.disable()
                )

                // Tắt form login
                .formLogin(form ->
                        form.disable()
                )

                .build();
    }
}