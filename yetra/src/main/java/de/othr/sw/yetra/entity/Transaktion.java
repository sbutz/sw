package de.othr.sw.yetra.entity;

import de.othr.sw.yetra.entity.util.SingleIdEntity;
import javax.validation.constraints.NotNull;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.PastOrPresent;
import java.util.Date;

@Entity
public class Transaktion extends SingleIdEntity<Long> {
    @Id
    private long nr;

    @NotNull
    @PastOrPresent
    private Date datum;

    @NotNull
    private float preis;

    @NotNull
    @ManyToOne
    private Wertpapier wertpapier;

    @NotNull
    @OneToOne
    private Auftrag kaufAuftrag;

    @NotNull
    @OneToOne
    private Auftrag verkaufAuftrag;

    public Transaktion() {
    }

    public Transaktion(float preis, Wertpapier wertpapier, Auftrag kaufAuftrag, Auftrag verkaufAuftrag) {
        this.datum = new Date();
        this.preis = preis;
        this.wertpapier = wertpapier;
        this.kaufAuftrag = kaufAuftrag;
        this.verkaufAuftrag = verkaufAuftrag;
    }

    public Long getNr() {
        return nr;
    }

    public void setNr(Long nr) {
        this.nr = nr;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public float getPreis() {
        return preis;
    }

    public void setPreis(float preis) {
        this.preis = preis;
    }

    public Wertpapier getWertpapier() {
        return wertpapier;
    }

    public void setWertpapier(Wertpapier wertpapier) {
        this.wertpapier = wertpapier;
    }

    public Auftrag getKaufAuftrag() {
        return kaufAuftrag;
    }

    public void setKaufAuftrag(Auftrag kaufAuftrag) {
        this.kaufAuftrag = kaufAuftrag;
    }

    public Auftrag getVerkaufAuftrag() {
        return verkaufAuftrag;
    }

    public void setVerkaufAuftrag(Auftrag verkaufAuftrag) {
        this.verkaufAuftrag = verkaufAuftrag;
    }

    @Override
    protected Long getID() {
        return this.nr;
    }
}
