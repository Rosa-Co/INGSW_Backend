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

    private IssueDTO(Builder builder) {
        this.id = builder.id;
        this.tipologia = builder.tipologia;
        this.titolo = builder.titolo;
        this.descrizione = builder.descrizione;
        this.img = builder.img;
        this.stato = builder.stato;
        this.priorita = builder.priorita;
        this.creataDa = builder.creataDa;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Integer id;
        private IssueType tipologia;
        private String titolo;
        private String descrizione;
        private String img;
        private IssueStatus stato;
        private IssuePriority priorita;
        private UtenteDTO creataDa;

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder tipologia(IssueType tipologia) {
            this.tipologia = tipologia;
            return this;
        }

        public Builder titolo(String titolo) {
            this.titolo = titolo;
            return this;
        }

        public Builder descrizione(String descrizione) {
            this.descrizione = descrizione;
            return this;
        }

        public Builder img(String img) {
            this.img = img;
            return this;
        }

        public Builder stato(IssueStatus stato) {
            this.stato = stato;
            return this;
        }

        public Builder priorita(IssuePriority priorita) {
            this.priorita = priorita;
            return this;
        }

        public Builder creataDa(UtenteDTO creataDa) {
            this.creataDa = creataDa;
            return this;
        }

        public IssueDTO build() {
            return new IssueDTO(this);
        }
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
