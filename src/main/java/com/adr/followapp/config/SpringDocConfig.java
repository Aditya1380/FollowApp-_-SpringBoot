package com.adr.followapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SpringDocConfig {

	@Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
        		.openapi("3.0.1")
                .info(new Info().title("Follow App Using Spring Boot")
                        .version("1.0")
                        .description("API documentation for My Spring Boot Application"));
    }
	
}
