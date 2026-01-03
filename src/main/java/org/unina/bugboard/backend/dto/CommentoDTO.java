package org.unina.bugboard.backend.dto;

import java.time.LocalDateTime;

public class CommentoDTO {
    private Integer id;
    private String descrizione;
    private LocalDateTime data;
    private UtenteDTO scrittoDa;
    private Integer appartieneId;

    public CommentoDTO() {
        // Default constructor
    }

    public CommentoDTO(Integer id, String descrizione, LocalDateTime data, UtenteDTO scrittoDa, Integer appartieneId) {
        this.id = id;
        this.descrizione = descrizione;
        this.data = data;
        this.scrittoDa = scrittoDa;
        this.appartieneId = appartieneId;
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

    public UtenteDTO getScrittoDa() {
        return scrittoDa;
    }

    public void setScrittoDa(UtenteDTO scrittoDa) {
        this.scrittoDa = scrittoDa;
    }

    public Integer getAppartieneId() {
        return appartieneId;
    }

    public void setAppartieneId(Integer appartieneId) {
        this.appartieneId = appartieneId;
    }
}
