package de.othr.sw.yetra.dto;

import de.othr.sw.yetra.entity.Wertpapier;

import java.util.Collection;

public class WertpapierDetails {
    private String isin;

    private String name;

    private float aktuellerPreis;

    private Collection<Kurswert> kurswerte;

    public WertpapierDetails(Wertpapier wertpapier, Collection<Kurswert> kurswerte) {
        this.isin = wertpapier.getIsin();
        this.name = wertpapier.getName();
        this.aktuellerPreis = wertpapier.getAktuellerPreis();
        this.kurswerte = kurswerte;
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getAktuellerPreis() {
        return aktuellerPreis;
    }

    public void setAktuellerPreis(float aktuellerPreis) {
        this.aktuellerPreis = aktuellerPreis;
    }

    public Collection<Kurswert> getKurswerte() {
        return kurswerte;
    }

    public void setKurswerte(Collection<Kurswert> kurswerte) {
        this.kurswerte = kurswerte;
    }
}
