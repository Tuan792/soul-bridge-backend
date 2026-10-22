package com.soulbridge.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI soulBridgeOpenApi() {
        return new OpenAPI().info(new Info()
                .title("SoulBridge Backend API")
                .version("v1")
                .description("Tài liệu nội bộ dành cho đội ngũ phát triển SoulBridge."));
    }
}
