package org.unina.bugboard.backend.dto;

import org.unina.bugboard.backend.model.enums.IssuePriority;
import org.unina.bugboard.backend.model.enums.IssueStatus;
import org.unina.bugboard.backend.model.enums.IssueType;

public class IssueDTO {
    private Integer id;
    private IssueType tipologia;
    private String titolo;
    private String descrizione;
    private String img;
    private IssueStatus stato;
    private IssuePriority priorita;
    private UtenteDTO creataDa;

    public IssueDTO() {
        // Default constructor
    }

    public IssueDTO(Integer id, IssueType tipologia, String titolo, String descrizione, String img, IssueStatus stato,
            IssuePriority priorita, UtenteDTO creataDa) {
        this.id = id;
        this.tipologia = tipologia;
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.img = img;
        this.stato = stato;
        this.priorita = priorita;
        this.creataDa = creataDa;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public IssueStatus getStato() {
        return stato;
    }

    public void setStato(IssueStatus stato) {
        this.stato = stato;
    }

    public IssuePriority getPriorita() {
        return priorita;
    }

    public void setPriorita(IssuePriority priorita) {
        this.priorita = priorita;
    }

    public UtenteDTO getCreataDa() {
        return creataDa;
    }

    public void setCreataDa(UtenteDTO creataDa) {
        this.creataDa = creataDa;
    }
}
