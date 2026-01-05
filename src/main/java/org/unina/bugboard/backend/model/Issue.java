package org.unina.bugboard.backend.model;

import jakarta.persistence.*;
import lombok.*;
import org.unina.bugboard.backend.model.enums.IssuePriority;
import org.unina.bugboard.backend.model.enums.IssueStatus;
import org.unina.bugboard.backend.model.enums.IssueType;

/**
 * Entità che rappresenta una segnalazione (Issue) nel sistema.
 */
@Entity
@Table(name = "issue")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Issue {

    /**
     * Identificativo univoco della segnalazione.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Tipologia della segnalazione (es. BUG, FEATURE).
     */
    @Enumerated(EnumType.STRING)
    private IssueType tipologia;

    /**
     * Titolo riassuntivo della segnalazione.
     */
    @Column(nullable = false)
    private String titolo;

    /**
     * Descrizione dettagliata della segnalazione.
     */
    @Column(columnDefinition = "TEXT")
    private String descrizione;

    /**
     * Percorso o URL dell'immagine allegata alla segnalazione.
     */
    private String img;

    /**
     * Stato attuale della segnalazione (es. OPEN, IN_PROGRESS, CLOSED).
     */
    @Enumerated(EnumType.STRING)
    private IssueStatus stato;

    /**
     * Priorità assegnata alla segnalazione (es. LOW, MEDIUM, HIGH).
     */
    @Enumerated(EnumType.STRING)
    private IssuePriority priorita;

    /**
     * Utente che ha creato la segnalazione.
     */
    @ManyToOne
    @JoinColumn(name = "creata_da", nullable = false)
    private Utente creataDa;
}
