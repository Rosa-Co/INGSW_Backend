package org.unina.bugboard.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.unina.bugboard.backend.model.enums.IssuePriority;
import org.unina.bugboard.backend.model.enums.IssueStatus;
import org.unina.bugboard.backend.model.enums.IssueType;

/**
 * Data Transfer Object (DTO) per rappresentare un'issue (segnalazione).
 * Contiene le informazioni di un'issue da restituire al client.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IssueDTO {
    /**
     * Identificativo univoco dell'issue.
     */
    private Integer id;

    /**
     * Tipo di issue (es. BUG, FEATURE, QUESTION).
     */
    private IssueType tipologia;

    /**
     * Titolo dell'issue.
     */
    private String titolo;

    /**
     * Descrizione dettagliata dell'issue.
     */
    private String descrizione;

    /**
     * URL o percorso dell'immagine allegata all'issue.
     */
    private String img;

    /**
     * Stato corrente dell'issue (es. TODO, IN_PROGRESS, DONE).
     */
    private IssueStatus stato;

    /**
     * Livello di priorità dell'issue (es. LOW, MEDIUM, HIGH).
     */
    private IssuePriority priorita;

    /**
     * DTO dell'utente che ha creato l'issue.
     */
    private UtenteDTO creataDa;
}
