package com.example.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI coreBankingOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Core Banking & Spring AI API")
                        .description("REST endpoints for customer onboarding, account operations, and BFF UI integration.")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("Banking Engineering Team")
                                .email("dev@example.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://spring.io")));
    }
}