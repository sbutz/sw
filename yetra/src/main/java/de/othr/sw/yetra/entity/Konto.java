package de.othr.sw.yetra.entity;

import jakarta.validation.constraints.NotNull;

import javax.persistence.Embeddable;

@Embeddable
public class Konto {
    @NotNull
    private String iban;

    public Konto() {

    }

    public Konto(String iban) {
        this.iban = iban;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }
}
