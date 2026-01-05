package org.unina.bugboard.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Oggetto risposta inviato dopo un login avvenuto con successo.
 * Contiene il token JWT e le informazioni dell'utente.
 */
@Data
@NoArgsConstructor
public class LoginResponse {
    /**
     * Token di accesso JWT (JSON Web Token).
     */
    private String token;

    /**
     * Tipo di token (default: Bearer).
     */
    private String type = "Bearer";

    /**
     * Identificativo dell'utente autenticato.
     */
    private Integer id;

    /**
     * Email dell'utente autenticato.
     */
    private String email;

    /**
     * Lista dei ruoli assegnati all'utente.
     */
    private List<String> roles;

    /**
     * Costruttore per creare una risposta di login.
     *
     * @param accessToken il token JWT generato
     * @param id          l'ID dell'utente
     * @param email       l'email dell'utente
     * @param roles       la lista dei ruoli dell'utente
     */
    public LoginResponse(String accessToken, Integer id, String email, List<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.email = email;
        this.roles = roles;
    }
}
