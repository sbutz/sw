package de.othr.sw.yetra.entity;

import javax.persistence.Embedded;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class Handelspartner extends Benutzer {
    @NotBlank
    private String apiSchluessel;

    @Embedded
    @NotNull
    private Konto rechnungsKonto;

    public Handelspartner() {
    }

    public Handelspartner(String apiSchluessel) {
        this.apiSchluessel = apiSchluessel;
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
