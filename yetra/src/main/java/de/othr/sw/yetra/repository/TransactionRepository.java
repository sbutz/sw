package de.othr.sw.yetra.repository;

import de.othr.sw.yetra.entity.Share;
import de.othr.sw.yetra.entity.Transaction;
import org.springframework.context.annotation.Scope;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

@Scope(SCOPE_SINGLETON)
public interface TransactionRepository extends PagingAndSortingRepository<Transaction, Long> {
    Optional<Transaction> getFirstByShareAndTimestampBetweenOrderByTimestampDesc(Share share, LocalDateTime start, LocalDateTime end);
}
