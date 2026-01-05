package org.unina.bugboard.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Oggetto richiesta per il login di un utente.
 * Contiene le credenziali (email e password).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    /**
     * Email dell'utente. Non può essere vuota e deve essere valida.
     */
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    private String email;

    /**
     * Password dell'utente. Non può essere vuota.
     */
    @NotBlank(message = "Password cannot be empty")
    private String password;
}
