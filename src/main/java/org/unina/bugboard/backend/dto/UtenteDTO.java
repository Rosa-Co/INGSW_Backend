package org.unina.bugboard.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.unina.bugboard.backend.model.enums.Role;

/**
 * Data Transfer Object (DTO) per rappresentare un utente.
 * Contiene le informazioni pubbliche di un utente.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UtenteDTO {
    /**
     * Identificativo univoco dell'utente.
     */
    private Integer id;

    /**
     * Email dell'utente.
     */
    private String email;

    /**
     * Ruolo dell'utente all'interno del sistema (es. ADMIN, USER).
     */
    private Role role;
}
