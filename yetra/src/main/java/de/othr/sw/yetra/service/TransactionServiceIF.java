package de.othr.sw.yetra.service;

import de.othr.sw.yetra.entity.Order;
import de.othr.sw.yetra.entity.Share;
import de.othr.sw.yetra.entity.Transaction;

import java.time.LocalDateTime;

public interface TransactionServiceIF {
    Transaction createTransaction(Order sellOrder, Order buyOrder) throws ServiceException;
    Transaction getLastTransaction(Share share, LocalDateTime start, LocalDateTime end) throws ServiceException;
}
