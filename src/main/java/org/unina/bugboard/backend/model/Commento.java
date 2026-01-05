package org.unina.bugboard.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Entità che rappresenta un commento associato a una segnalazione.
 */
@Entity
@Table(name = "commento")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Commento {

    /**
     * Identificativo univoco del commento.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Descrizione testuale del commento.
     */
    @Column(columnDefinition = "TEXT", nullable = false)
    private String descrizione;

    /**
     * Data e ora di creazione del commento. Default: data e ora attuale.
     */
    private LocalDateTime data = LocalDateTime.now();

    /**
     * Utente autore del commento.
     */
    @ManyToOne
    @JoinColumn(name = "scritto_da", nullable = false)
    private Utente scrittoDa;

    /**
     * Segnalazione (Issue) a cui il commento appartiene.
     */
    @ManyToOne
    @JoinColumn(name = "appartiene", nullable = false)
    private Issue appartiene;

}
