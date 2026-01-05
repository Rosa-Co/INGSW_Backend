package org.unina.bugboard.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.unina.bugboard.backend.model.enums.IssuePriority;
import org.unina.bugboard.backend.model.enums.IssueStatus;
import org.unina.bugboard.backend.model.enums.IssueType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IssueDTO {
    private Integer id;
    private IssueType tipologia;
    private String titolo;
    private String descrizione;
    private String img;
    private IssueStatus stato;
    private IssuePriority priorita;
    private UtenteDTO creataDa;
}
