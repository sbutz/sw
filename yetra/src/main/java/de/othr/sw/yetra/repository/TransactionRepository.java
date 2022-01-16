package de.othr.sw.yetra.repository;

import de.othr.sw.yetra.entity.Share;
import de.othr.sw.yetra.entity.Transaction;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TransactionRepository extends PagingAndSortingRepository<Transaction, Long> {
    Optional<Transaction> getFirstByShareAndTimestampBetweenOrderByTimestampDesc(Share share, LocalDateTime start, LocalDateTime end);
}
