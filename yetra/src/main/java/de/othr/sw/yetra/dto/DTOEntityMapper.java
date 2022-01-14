package de.othr.sw.yetra.dto;

public interface DTOEntityMapper<E,D> {
    D toDTO(E entity);
    E fromDTO(D dto);
}
