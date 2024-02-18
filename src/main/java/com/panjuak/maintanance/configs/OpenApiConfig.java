package com.panjuak.maintanance.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI swaggerConfig(){
        return new OpenAPI()
                .info(new Info().title("API Maintenance")
                        .description("API Maintenance collection")
                        .version("1.0.0"));
    }
}
