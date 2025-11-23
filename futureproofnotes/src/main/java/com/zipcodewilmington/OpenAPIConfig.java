package com.zipcodewilmington;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Future-Proof Notes API",
                version = "1.0",
                description = "REST API for a personal notes manager with metadata, search, tags, and statistics.",
                contact = @Contact(
                        name = "Devesh Murali",
                        email = "example@email.com"
                ),
                license = @License(
                        name = "MIT License",
                        url = "https://opensource.org/licenses/MIT"
                )
        )
)

public class OpenAPIConfig {
    
}
