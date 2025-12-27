package org.unina.bugboard.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "commento")
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

    public Commento() {
    }

    public Commento(Integer id, String descrizione, LocalDateTime data, Utente scrittoDa, Issue appartiene) {
        this.id = id;
        this.descrizione = descrizione;
        this.data = data;
        this.scrittoDa = scrittoDa;
        this.appartiene = appartiene;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public Utente getScrittoDa() {
        return scrittoDa;
    }

    public void setScrittoDa(Utente scrittoDa) {
        this.scrittoDa = scrittoDa;
    }

    public Issue getAppartiene() {
        return appartiene;
    }

    public void setAppartiene(Issue appartiene) {
        this.appartiene = appartiene;
    }
}
