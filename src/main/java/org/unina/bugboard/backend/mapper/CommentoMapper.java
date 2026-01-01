package org.unina.bugboard.backend.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.unina.bugboard.backend.dto.CommentoDTO;
import org.unina.bugboard.backend.model.Commento;

@Component
public class CommentoMapper {

    private final UtenteMapper utenteMapper;

    @Autowired
    public CommentoMapper(UtenteMapper utenteMapper) {
        this.utenteMapper = utenteMapper;
    }

    public CommentoDTO toDTO(Commento commento) {
        if (commento == null) {
            return null;
        }
        return new CommentoDTO(
                commento.getId(),
                commento.getDescrizione(),
                commento.getData(),
                utenteMapper.toDTO(commento.getScrittoDa()),
                commento.getAppartiene() != null ? commento.getAppartiene().getId() : null);
    }
}
