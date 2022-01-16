package de.othr.sw.yetra.service.impl;

import de.othr.sw.yetra.entity.*;
import de.othr.sw.yetra.repository.TransactionRepository;
import de.othr.sw.yetra.service.ServiceException;
import de.othr.sw.yetra.service.TransactionServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
public class TransactionService implements TransactionServiceIF {
    @Autowired
    private TransactionRepository transactionRepo;

    @Override
    @Transactional(Transactional.TxType.MANDATORY)
    public Transaction createTransaction(Order buyOrder, Order sellOrder) throws ServiceException {
        if (buyOrder.getStatus() != OrderStatus.OPEN || sellOrder.getStatus() != OrderStatus.OPEN)
            throw new ServiceException(422, "Order already closed");
        if (buyOrder.getType() != OrderType.BUY)
            throw new ServiceException(422, "Wrong order type");
        if (sellOrder.getType() != OrderType.SELL)
            throw new ServiceException(422, "Wrong order type");
        if (! buyOrder.getShare().equals(sellOrder.getShare()))
            throw new ServiceException(422, "Different shares specified");
        if (buyOrder.getQuantity() != sellOrder.getQuantity())
            throw new ServiceException(422, "Different quantities specified");
        if (buyOrder.getUnitPrice() != sellOrder.getUnitPrice())
            throw new ServiceException(422, "Different unit prices specified");

        Transaction transaction = new Transaction(sellOrder.getShare(), sellOrder.getUnitPrice(), buyOrder, sellOrder);
        return transactionRepo.save(transaction);
    }

    @Override
    public Page<Transaction> getTransactions(Pageable pageable) {
        return transactionRepo.findAll(pageable);
    }

    @Override
    public Transaction getLastTransaction(Share share, LocalDateTime start, LocalDateTime end) throws ServiceException {
        return transactionRepo.getFirstByShareAndTimestampBetweenOrderByTimestampDesc(share, start, end)
                .orElseThrow(()-> {
                    throw new ServiceException(404, "Transaction not found");
                });
    }
}
