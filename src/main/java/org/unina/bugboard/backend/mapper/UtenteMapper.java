package org.unina.bugboard.backend.mapper;

import org.springframework.stereotype.Component;
import org.unina.bugboard.backend.dto.UserCreationRequest;
import org.unina.bugboard.backend.dto.UtenteDTO;
import org.unina.bugboard.backend.model.Utente;
import org.unina.bugboard.backend.model.enums.Role;

@Component
public class UtenteMapper {

    public UtenteDTO toDTO(Utente utente) {
        if (utente == null) {
            return null;
        }
        return new UtenteDTO(
                utente.getId(),
                utente.getEmail(),
                utente.getRole());
    }

    public Utente toEntity(UserCreationRequest request) {
        if (request == null) {
            return null;
        }
        Utente utente = new Utente();
        utente.setEmail(request.getEmail());
        utente.setPassword(request.getPassword()); // Ideally hashed here or in service
        utente.setRole(request.getRole());


        return utente;
    }
}
