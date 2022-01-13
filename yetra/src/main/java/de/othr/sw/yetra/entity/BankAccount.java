package de.othr.sw.yetra.entity;

import javax.validation.constraints.NotNull;

import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

@Embeddable
public class BankAccount {
    @NotNull
    @Size(min = 4, max = 34)
    private String iban;

    public BankAccount() {

    }

    public BankAccount(String iban) {
        this.iban = iban;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }
}
