package de.othr.sw.yetra.repo;

import de.othr.sw.yetra.entity.Auftrag;
import org.springframework.data.repository.CrudRepository;

public interface AuftragsRepository extends CrudRepository<Auftrag, Long> {
}
