package de.othr.sw.yetra.service;

import de.othr.sw.yetra.entity.Order;
import de.othr.sw.yetra.entity.OrderStatus;
import de.othr.sw.yetra.entity.OrderType;
import de.othr.sw.yetra.entity.Transaction;
import de.othr.sw.yetra.repo.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class TransactionService implements TransactionServiceIF {
    @Autowired
    TransactionRepository transactionRepo;

    @Override
    @Transactional(Transactional.TxType.MANDATORY)
    public Transaction createTransaction(Order buyOrder, Order sellOrder) throws ServiceException {
        if (buyOrder.getStatus() != OrderStatus.OPEN || sellOrder.getStatus() != OrderStatus.OPEN)
            throw new ServiceException(422, "Order already closed");
        if (buyOrder.getType() != OrderType.BUY)
            throw new ServiceException(422, "Wrong order type");
        if (sellOrder.getType() != OrderType.SELL)
            throw new ServiceException(422, "Wrong order type");
        if (buyOrder.getShare() != sellOrder.getShare())
            throw new ServiceException(422, "Different shares specified");
        if (buyOrder.getQuantity() != sellOrder.getQuantity())
            throw new ServiceException(422, "Different quantities specified");
        if (buyOrder.getUnitPrice() != sellOrder.getUnitPrice())
            throw new ServiceException(422, "Different unit prices specified");

        Transaction transaction = new Transaction(sellOrder.getShare(), sellOrder.getUnitPrice(), buyOrder, sellOrder);
        return transactionRepo.save(transaction);
    }
}
