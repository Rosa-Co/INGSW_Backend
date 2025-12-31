package org.unina.bugboard.backend.dto;

public class IssueDTO {
    private Integer id;
    private String tipologia;
    private String titolo;
    private String descrizione;
    private String img;
    private String stato;
    private String priorita;
    private UtenteDTO creataDa;

    public IssueDTO() {
    }

    public IssueDTO(Integer id, String tipologia, String titolo, String descrizione, String img, String stato,
            String priorita, UtenteDTO creataDa) {
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

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
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

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public String getPriorita() {
        return priorita;
    }

    public void setPriorita(String priorita) {
        this.priorita = priorita;
    }

    public UtenteDTO getCreataDa() {
        return creataDa;
    }

    public void setCreataDa(UtenteDTO creataDa) {
        this.creataDa = creataDa;
    }
}
