package de.othr.sw.yetra.service;

import de.othr.sw.yetra.entity.Order;
import de.othr.sw.yetra.entity.Share;
import de.othr.sw.yetra.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface TransactionServiceIF {
    Transaction createTransaction(Order sellOrder, Order buyOrder) throws ServiceException;
    Page<Transaction> getTransactions(Pageable pageable);
    Transaction getLastTransaction(Share share, LocalDateTime start, LocalDateTime end) throws ServiceException;
}
