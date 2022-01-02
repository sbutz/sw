package de.othr.sw.yetra.entity;

import javax.persistence.Entity;

@Entity
public class Angestellter extends Benutzer {
    //TODO: add rechte
    public Angestellter() {
    }

    public Angestellter(long nr) {
        this.nr = nr;
    }

    public Angestellter(String nutzername, String passwort) {
        super(nutzername, passwort);
    }
}
