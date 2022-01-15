package de.othr.sw.yetra.repo;

import de.othr.sw.yetra.entity.Share;
import de.othr.sw.yetra.entity.Transaction;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    Optional<Transaction> getFirstByShareAndTimestampBetweenOrderByTimestampDesc(Share share, LocalDateTime start, LocalDateTime end);
}
