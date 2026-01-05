package org.unina.bugboard.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configurazione Web MVC.
 * Gestisce le impostazioni CORS per consentire le chiamate dal frontend.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Configura le regole CORS.
     * Consente tutte le origini e i principali metodi HTTP.
     *
     * @param registry il registro CORS da configurare
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
    }
}
