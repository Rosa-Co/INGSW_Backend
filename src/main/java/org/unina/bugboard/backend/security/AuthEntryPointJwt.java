package org.unina.bugboard.backend.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Componente che gestisce i tentativi di accesso non autorizzati.
 * Implementa AuthenticationEntryPoint per intercettare gli errori di
 * autenticazione.
 */
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

    /**
     * Metodo invocato quando un utente tenta di accedere a una risorsa protetta
     * senza autenticazione.
     * Restituisce un errore HTTP 401 Unauthorized.
     *
     * @param request       La richiesta HTTP che ha causato l'errore.
     * @param response      La risposta HTTP in cui scrivere l'errore.
     * @param authException L'eccezione di autenticazione sollevata.
     * @throws IOException      Se si verifica un errore di input/output.
     * @throws ServletException Se si verifica un errore nella servlet.
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException)
            throws IOException, ServletException {
        logger.error("Unauthorized error: {}", authException.getMessage());
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");
    }
}
