package org.example.bookservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Book Service API")
                        .version("1.0.0")
                        .description("Documentation for managing books, users, and orders in the library system.")
                        .contact(new Contact()
                                .name("Oleksandr")
                                .email("oleksandrhelevan@gmail.com"))
                );
    }
}
