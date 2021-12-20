package de.othr.sw.yetra.repo;

import de.othr.sw.yetra.entity.Benutzer;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BenutzerRepository extends CrudRepository<Benutzer, Long> {
    Optional<Benutzer> findBenutzerByNutzername(String nutzername);
}
