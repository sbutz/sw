package de.othr.sw.yetra.entity;

import de.othr.sw.yetra.entity.util.SingleIdEntity;
import jakarta.validation.constraints.NotNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public abstract class Benutzer extends SingleIdEntity<Long> {
    @Id
    @NotNull
    @GeneratedValue
    private long nr;

    public Benutzer() {
    }

    public Benutzer(long nr) {
        this.nr = nr;
    }

    @Override
    protected Long getID() {
        return this.nr;
    }
}
