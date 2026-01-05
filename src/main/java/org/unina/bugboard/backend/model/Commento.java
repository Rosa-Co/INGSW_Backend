package org.unina.bugboard.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "commento")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Commento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String descrizione;

    private LocalDateTime data = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "scritto_da", nullable = false)
    private Utente scrittoDa;

    @ManyToOne
    @JoinColumn(name = "appartiene", nullable = false)
    private Issue appartiene;

}
