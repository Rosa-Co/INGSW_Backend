package org.unina.bugboard.backend.model;

import jakarta.persistence.*;
import org.unina.bugboard.backend.model.enums.IssuePriority;
import org.unina.bugboard.backend.model.enums.IssueStatus;
import org.unina.bugboard.backend.model.enums.IssueType;

@Entity
@Table(name = "issue")
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private IssueType tipologia;

    @Column(nullable = false)
    private String titolo;

    @Column(columnDefinition = "TEXT")
    private String descrizione;

    private String img;

    @Enumerated(EnumType.STRING)
    private IssueStatus stato;

    @Enumerated(EnumType.STRING)
    private IssuePriority priorita;

    @ManyToOne
    @JoinColumn(name = "creata_da", nullable = false)
    private Utente creataDa;

    public Issue() {
    }

    public Issue(Integer id, IssueType tipologia, String titolo, String descrizione, String img, IssueStatus stato,
            IssuePriority priorita, Utente creataDa) {
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

    public Utente getCreataDa() {
        return creataDa;
    }

    public void setCreataDa(Utente creataDa) {
        this.creataDa = creataDa;
    }
}
