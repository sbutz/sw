package de.othr.sw.yetra.entity;

import de.othr.sw.yetra.entity.util.SingleIdEntity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Wertpapier extends SingleIdEntity<String> {
    @Id
    @NotNull
    @Size(max=12)
    private String isin;

    @NotNull
    private String name;

    private float aktuellerPreis = Float.NaN;

    public Wertpapier() {

    }

    public Wertpapier(String isin) {
        this.isin = isin;
    }

    public Wertpapier(String isin, String name) {
        this.isin = isin;
        this.name = name;
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

    @Override
    protected String getID() {
        return this.isin;
    }
}