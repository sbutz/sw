package de.othr.sw.yetra.repo;

import de.othr.sw.yetra.entity.Transaktion;
import org.springframework.data.repository.CrudRepository;

public interface TransaktionsRepository extends CrudRepository<Transaktion, Long> {
}
