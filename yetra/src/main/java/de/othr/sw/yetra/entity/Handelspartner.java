package de.othr.sw.yetra.entity;

import jakarta.validation.constraints.NotNull;

public class Handelspartner extends Benutzer {
    @NotNull
    private String apiSchluessel;

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
