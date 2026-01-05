package org.unina.bugboard.backend.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.unina.bugboard.backend.dto.IssueDTO;
import org.unina.bugboard.backend.model.Issue;

/**
 * Componente responsabile della mappatura tra l'entità Issue e il DTO IssueDTO.
 */
@Component
public class IssueMapper {

    private final UtenteMapper utenteMapper;

    @Autowired
    public IssueMapper(UtenteMapper utenteMapper) {
        this.utenteMapper = utenteMapper;
    }

    /**
     * Converte un'entità Issue in un IssueDTO.
     *
     * @param issue l'entità Issue da convertire
     * @return l'IssueDTO corrispondente, o null se l'input è null
     */
    public IssueDTO toDTO(Issue issue) {
        if (issue == null) {
            return null;
        }
        return IssueDTO.builder()
                .id(issue.getId())
                .tipologia(issue.getTipologia())
                .titolo(issue.getTitolo())
                .descrizione(issue.getDescrizione())
                .img(issue.getImg())
                .stato(issue.getStato())
                .priorita(issue.getPriorita())
                .creataDa(utenteMapper.toDTO(issue.getCreataDa()))
                .build();
    }
}
