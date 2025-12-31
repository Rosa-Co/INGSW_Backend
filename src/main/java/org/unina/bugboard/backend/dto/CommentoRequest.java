package org.unina.bugboard.backend.dto;

public class CommentoRequest {
    private String descrizione;
    private Integer appartieneId;

    public CommentoRequest() {
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Integer getAppartieneId() {
        return appartieneId;
    }

    public void setAppartieneId(Integer appartieneId) {
        this.appartieneId = appartieneId;
    }
}
