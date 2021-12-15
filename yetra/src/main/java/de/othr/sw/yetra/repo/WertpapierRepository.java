package de.othr.sw.yetra.repo;

import de.othr.sw.yetra.entity.Wertpapier;
import org.springframework.data.repository.CrudRepository;

public interface WertpapierRepository extends CrudRepository<Wertpapier, String> {
}
