package de.othr.sw.yetra.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Handelspartner extends Benutzer {
    //TODO: generating keys
    @NotBlank
    @Column(unique=true)
    private String apiSchluessel;

    @Embedded
    @NotNull
    private Konto rechnungsKonto;

    public Handelspartner() {
    }

    public Handelspartner(Konto rechnungsKonto) {
        this.rechnungsKonto = rechnungsKonto;
    }

    public String getApiSchluessel() {
        return apiSchluessel;
    }

    public void setApiSchluessel(String apiSchluessel) {
        this.apiSchluessel = apiSchluessel;
    }

    public Konto getRechnungsKonto() {
        return rechnungsKonto;
    }

    public void setRechnungsKonto(Konto rechnungsKonto) {
        this.rechnungsKonto = rechnungsKonto;
    }
}
