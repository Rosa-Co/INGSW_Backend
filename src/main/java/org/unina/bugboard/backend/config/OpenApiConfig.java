package org.unina.bugboard.backend.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configurazione per OpenAPI (Swagger).
 * Definisce le informazioni dell'API e lo schema di sicurezza (JWT).
 */
@Configuration
public class OpenApiConfig {

        /**
         * Bean per la configurazione personalizzata di OpenAPI.
         * Imposta il titolo, la versione, la descrizione e lo schema di sicurezza JWT.
         *
         * @return l'oggetto OpenAPI configurato
         */
        @Bean
        public OpenAPI customOpenAPI() {
                final String securitySchemeName = "bearerAuth";
                return new OpenAPI()
                                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                                .components(new Components()
                                                .addSecuritySchemes(securitySchemeName,
                                                                new SecurityScheme()
                                                                                .name(securitySchemeName)
                                                                                .type(SecurityScheme.Type.HTTP)
                                                                                .scheme("bearer")
                                                                                .bearerFormat("JWT")))
                                .info(new Info()
                                                .title("Bugboard API")
                                                .version("1.0")
                                                .description("API documentation for Bugboard Backend"));
        }
}
