package org.unina.bugboard.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import org.unina.bugboard.backend.model.enums.IssuePriority;
import org.unina.bugboard.backend.model.enums.IssueStatus;
import org.unina.bugboard.backend.model.enums.IssueType;

public class IssueRequest {
    @NotBlank(message = "Title cannot be empty")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    private String titolo;

    @NotBlank(message = "Description cannot be empty")
    private String descrizione;

    private IssueType tipologia;
    private String img;
    private IssuePriority priorita;
    private IssueStatus stato; // Optional for update

    public IssueRequest() {
    }

    public IssueType getTipologia() {
        return tipologia;
    }

    public void setTipologia(IssueType tipologia) {
        this.tipologia = tipologia;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public IssuePriority getPriorita() {
        return priorita;
    }

    public void setPriorita(IssuePriority priorita) {
        this.priorita = priorita;
    }

    public IssueStatus getStato() {
        return stato;
    }

    public void setStato(IssueStatus stato) {
        this.stato = stato;
    }
}
