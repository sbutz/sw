package de.othr.sw.yetra.entity;

import javax.persistence.Entity;

@Entity
public class Angestellter extends Benutzer {
    //TODO: add rechte
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
