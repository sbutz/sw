package de.othr.sw.yetra.service;

import de.othr.sw.yetra.entity.Order;
import de.othr.sw.yetra.entity.Transaction;

public interface TransactionServiceIF {
    Transaction createTransaction(Order sellOrder, Order buyOrder) throws ServiceException;
    //TODO: list transactions
}
