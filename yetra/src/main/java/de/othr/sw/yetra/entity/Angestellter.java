package de.othr.sw.yetra.entity;

import jakarta.validation.constraints.NotNull;

public class Angestellter extends Benutzer{
    @NotNull
    private String nutzername;

    @NotNull
    private String passwort;

    public Angestellter() {
    }

    public Angestellter(String nutzername, String passwort) {
        this.nutzername = nutzername;
        this.passwort = passwort;
    }

    public String getNutzername() {
        return nutzername;
    }

    public void setNutzername(String nutzername) {
        this.nutzername = nutzername;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }
}
