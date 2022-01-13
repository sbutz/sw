package de.othr.sw.yetra.entity;

import javax.persistence.Entity;

@Entity
public class Employee extends User {
    //TODO: add privileges
    public Employee() {
    }

    public Employee(long nr) {
        this.id = nr;
    }

    public Employee(String nutzername, String passwort) {
        super(nutzername, passwort);
    }
}
