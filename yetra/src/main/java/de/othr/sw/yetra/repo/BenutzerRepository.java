package de.othr.sw.yetra.repo;

import de.othr.sw.yetra.entity.Benutzer;
import org.springframework.data.repository.CrudRepository;

public interface BenutzerRepository extends CrudRepository<Benutzer, Long> {
}
