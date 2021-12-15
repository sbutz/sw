package de.othr.sw.yetra.dto;

import java.util.Date;

public class Kurswert {
    private Date zeitstempel;

    private float wert;

    public Kurswert(Date zeitstempel, float wert) {
        this.zeitstempel = zeitstempel;
        this.wert = wert;
    }

    public Date getZeitstempel() {
        return zeitstempel;
    }

    public void setZeitstempel(Date zeitstempel) {
        this.zeitstempel = zeitstempel;
    }

    public float getWert() {
        return wert;
    }

    public void setWert(float wert) {
        this.wert = wert;
    }
}
