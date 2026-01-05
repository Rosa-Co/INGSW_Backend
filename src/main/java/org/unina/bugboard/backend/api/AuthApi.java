package org.unina.bugboard.backend.api;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.unina.bugboard.backend.dto.LoginRequest;
import org.unina.bugboard.backend.dto.LoginResponse;

/**
 * Interfaccia API per l'autenticazione.
 * Definisce i contratti per gli endpoint di login.
 */
@RequestMapping("/api/auth")
public interface AuthApi {

    /**
     * Endpoint per il login degli utenti.
     *
     * @param loginRequest la richiesta di login contenente email e password
     * @return 200 OK con il token JWT se l'autenticazione ha successo
     */
    @PostMapping("/login")
    ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest);
}
