package org.unina.bugboard.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.unina.bugboard.backend.model.enums.Role;

/**
 * Oggetto richiesta per la creazione (registrazione) di un nuovo utente.
 * Contiene i dati necessari per creare un account.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreationRequest {
    /**
     * Email del nuovo utente. Deve essere valida e non vuota.
     */
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    private String email;

    /**
     * Password del nuovo utente. Deve avere una lunghezza minima.
     */
    @NotBlank(message = "Password cannot be empty")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    /**
     * Ruolo assegnato al nuovo utente (es. USER, ADMIN).
     */
    @NotNull(message = "Role cannot be null")
    private Role role;
}
