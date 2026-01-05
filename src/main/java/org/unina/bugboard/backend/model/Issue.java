package org.unina.bugboard.backend.model;

import jakarta.persistence.*;
import lombok.*;
import org.unina.bugboard.backend.model.enums.IssuePriority;
import org.unina.bugboard.backend.model.enums.IssueStatus;
import org.unina.bugboard.backend.model.enums.IssueType;

@Entity
@Table(name = "issue")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private IssueType tipologia;

    @Column(nullable = false)
    private String titolo;

    @Column(columnDefinition = "TEXT")
    private String descrizione;

    private String img;

    @Enumerated(EnumType.STRING)
    private IssueStatus stato;

    @Enumerated(EnumType.STRING)
    private IssuePriority priorita;

    @ManyToOne
    @JoinColumn(name = "creata_da", nullable = false)
    private Utente creataDa;
}
