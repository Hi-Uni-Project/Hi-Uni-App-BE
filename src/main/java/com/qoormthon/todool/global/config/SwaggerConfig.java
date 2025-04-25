package com.qoormthon.todool.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .in(SecurityScheme.In.HEADER)
                                        .name("Authorization"))
                )
                .info(new Info()
                        .title("To-Dool API")
                        .description("API 호출에는 JWT 인증이 필요합니다.(role USER/ADMIN)")
                        .version("1.0.0"))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
    }

    private Info apiInfo() {
        return new Info()
                .title("To-Dool Swagger")
                .description("To-Dool Backend API documentation")
                .version("1.0.0");
    }
}
