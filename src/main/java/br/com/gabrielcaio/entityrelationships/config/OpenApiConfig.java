package br.com.gabrielcaio.entityrelationships.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API - Entity Relationships",
                version = "1.0",
                contact = @Contact(
                        name = "Gabriel Caio",
                        email = "gabrielcaio848@gmail.com",
                        url = "https://github.com/gabrielcaio11"

                ),
                description = """
                        Mapeamento e consulta de dados com diferentes relacionamentos (Um-para-Um, Um-para-Muitos, Muitos-para-Muitos).
                        """
        ), security = {
            @SecurityRequirement(name = "bearerAuth")
        }
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer",
        in = SecuritySchemeIn.HEADER
)

public class OpenApiConfig {
}
