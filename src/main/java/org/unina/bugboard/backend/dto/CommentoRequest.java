package org.unina.bugboard.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CommentoRequest {
    @NotBlank(message = "Description cannot be empty")
    private String descrizione;

    @NotNull(message = "Issue ID is mandatory")
    private Integer appartieneId;

    public CommentoRequest() {
        // Default constructor
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
