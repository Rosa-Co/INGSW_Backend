package org.unina.bugboard.backend.mapper;

import org.springframework.stereotype.Component;
import org.unina.bugboard.backend.dto.UtenteDTO;
import org.unina.bugboard.backend.model.Utente;

@Component
public class UtenteMapper {

    public UtenteDTO toDTO(Utente utente) {
        if (utente == null) {
            return null;
        }
        return new UtenteDTO(
                utente.getId(),
                utente.getEmail(),
                utente.getRole() != null ? utente.getRole().name() : null);
    }
}
