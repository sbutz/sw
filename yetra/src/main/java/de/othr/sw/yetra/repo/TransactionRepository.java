package de.othr.sw.yetra.repo;

import de.othr.sw.yetra.entity.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {
}
