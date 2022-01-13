package de.othr.sw.yetra.entity.util;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
//TODO: comparable interface
public abstract class SingleIdEntity<T> implements Serializable {

    protected abstract T getId();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SingleIdEntity<T> that = (SingleIdEntity<T>) o;

        return this.getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return this.getId().hashCode();
    }
}
