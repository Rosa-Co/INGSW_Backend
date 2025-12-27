package org.unina.bugboard.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "issue")
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String tipologia;

    @Column(nullable = false)
    private String titolo;

    @Column(columnDefinition = "TEXT")
    private String descrizione;

    private String img;

    private String stato;

    private String priorita;

    @ManyToOne
    @JoinColumn(name = "creata_da", nullable = false)
    private Utente creataDa;

    public Issue() {
    }

    public Issue(Integer id, String tipologia, String titolo, String descrizione, String img, String stato,
            String priorita, Utente creataDa) {
        this.id = id;
        this.tipologia = tipologia;
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.img = img;
        this.stato = stato;
        this.priorita = priorita;
        this.creataDa = creataDa;
    }

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

    public Utente getCreataDa() {
        return creataDa;
    }

    public void setCreataDa(Utente creataDa) {
        this.creataDa = creataDa;
    }
}
