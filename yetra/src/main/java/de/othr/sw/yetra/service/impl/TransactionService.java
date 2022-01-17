package de.othr.sw.yetra.service.impl;

import de.othr.sw.yetra.entity.*;
import de.othr.sw.yetra.repository.TransactionRepository;
import de.othr.sw.yetra.service.TransactionServiceIF;
import de.othr.sw.yetra.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

@Service
@Scope(SCOPE_SINGLETON)
public class TransactionService implements TransactionServiceIF {

    @Autowired
    private TransactionRepository transactionRepo;

    @Override
    @Transactional(Transactional.TxType.MANDATORY)
    public Transaction createTransaction(Order buyOrder, Order sellOrder) throws ServiceException {
        if (buyOrder.getStatus() != OrderStatus.OPEN || sellOrder.getStatus() != OrderStatus.OPEN)
            throw new InvalidEntityException("Order already closed");
        if (buyOrder.getType() != OrderType.BUY)
            throw new InvalidEntityException("Wrong order type");
        if (sellOrder.getType() != OrderType.SELL)
            throw new InvalidEntityException("Wrong order type");
        if (! buyOrder.getShare().equals(sellOrder.getShare()))
            throw new InvalidEntityException("Different shares specified");
        if (buyOrder.getQuantity() != sellOrder.getQuantity())
            throw new InvalidEntityException("Different quantities specified");
        if (buyOrder.getUnitPrice() != sellOrder.getUnitPrice())
            throw new InvalidEntityException("Different unit prices specified");

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
                    throw new NotFoundException("Transaction not found");
                });
    }
}
