package org.unina.bugboard.backend.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.unina.bugboard.backend.dto.IssueDTO;
import org.unina.bugboard.backend.model.Issue;

@Component
public class IssueMapper {

    private final UtenteMapper utenteMapper;

    @Autowired
    public IssueMapper(UtenteMapper utenteMapper) {
        this.utenteMapper = utenteMapper;
    }

    public IssueDTO toDTO(Issue issue) {
        if (issue == null) {
            return null;
        }
        return new IssueDTO(
                issue.getId(),
                issue.getTipologia(),
                issue.getTitolo(),
                issue.getDescrizione(),
                issue.getImg(),
                issue.getStato(),
                issue.getPriorita(),
                utenteMapper.toDTO(issue.getCreataDa()));
    }
}
