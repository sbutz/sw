package de.othr.sw.yetra.entity;

import de.othr.sw.yetra.entity.util.SingleIdEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public abstract class Benutzer extends SingleIdEntity<Long> {
    @Id
    @GeneratedValue
    private long nr;

    public Benutzer() {
    }

    @Override
    protected Long getID() {
        return this.nr;
    }
}
