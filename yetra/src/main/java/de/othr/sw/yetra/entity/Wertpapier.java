package de.othr.sw.yetra.entity;

import de.othr.sw.yetra.entity.util.SingleIdEntity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Entity
public class Wertpapier extends SingleIdEntity<String> {
    @Id
    @NotNull
    @Size(min = 12, max = 12)
    private String isin;

    @NotBlank
    private String name;

    @PositiveOrZero
    private float aktuellerPreis;

    public Wertpapier() {

    }

    public Wertpapier(String isin) {
        this.isin = isin;
    }

    public Wertpapier(String isin, String name, float aktuellerPreis) {
        this.isin = isin;
        this.name = name;
        this.aktuellerPreis = aktuellerPreis;
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