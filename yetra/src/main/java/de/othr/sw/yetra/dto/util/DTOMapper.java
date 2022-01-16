package de.othr.sw.yetra.dto.util;

public interface DTOMapper<E,D> {
    D toDTO(E entity);
    E fromDTO(D dto);
}
