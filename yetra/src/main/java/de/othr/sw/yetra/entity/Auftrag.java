package de.othr.sw.yetra.entity;

import de.othr.sw.yetra.entity.util.SingleIdEntity;

import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

@Entity
public class Auftrag extends SingleIdEntity<Long> {
    @Id
    @GeneratedValue
    private long nr;

    @Enumerated(EnumType.STRING)
    @NotNull
    private AuftragsTyp typ;

    @ManyToOne
    private Wertpapier wertpapier;

    @NotNull
    private int menge;

    @NotNull
    //TODO: not zero
    //@Positive
    //@Negative
    private float stueckPreis;

    @Enumerated(EnumType.STRING)
    @NotNull
    private AuftragsStatus status;

    @ManyToOne
    private Benutzer auftraggeber;

    @NotNull
    @PastOrPresent
    private Date datum;

    private String benachrichtigungsUrl;

    @Embedded
    @NotNull
    private Konto kundenKonto;

    public Auftrag() {
    }

    public Auftrag(AuftragsTyp typ, Wertpapier wertpapier, int menge, float stueckPreis, Benutzer auftraggeber, String benachrichtigungsUrl, Konto kundenKonto) {
        this.typ = typ;
        this.wertpapier = wertpapier;
        this.menge = menge;
        this.stueckPreis = stueckPreis;
        this.status = AuftragsStatus.OFFEN;
        this.auftraggeber = auftraggeber;
        this.datum = new Date();
        this.benachrichtigungsUrl = benachrichtigungsUrl;
        this.kundenKonto = kundenKonto;
    }

    public Long getNr() {
        return nr;
    }

    public void setNr(Long nr) {
        this.nr = nr;
    }

    public AuftragsTyp getTyp() {
        return typ;
    }

    public void setTyp(AuftragsTyp typ) {
        this.typ = typ;
    }

    public Wertpapier getWertpapier() {
        return wertpapier;
    }

    public void setWertpapier(Wertpapier wertpapier) {
        this.wertpapier = wertpapier;
    }

    public int getMenge() {
        return menge;
    }

    public void setMenge(int menge) {
        this.menge = menge;
    }

    public float getStueckPreis() {
        return stueckPreis;
    }

    public void setStueckPreis(float stueckPreis) {
        this.stueckPreis = stueckPreis;
    }

    public AuftragsStatus getStatus() {
        return status;
    }

    public void setStatus(AuftragsStatus status) {
        this.status = status;
    }

    public Benutzer getAuftraggeber() {
        return auftraggeber;
    }

    public void setAuftraggeber(Benutzer auftraggeber) {
        this.auftraggeber = auftraggeber;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public String getBenachrichtigungsUrl() {
        return benachrichtigungsUrl;
    }

    public void setBenachrichtigungsUrl(String benachrichtigungsUrl) {
        this.benachrichtigungsUrl = benachrichtigungsUrl;
    }

    public Konto getKundenKonto() {
        return kundenKonto;
    }

    public void setKundenKonto(Konto kundenKonto) {
        this.kundenKonto = kundenKonto;
    }

    @Override
    protected Long getID() {
        return this.nr;
    }
}
