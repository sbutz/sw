package de.othr.sw.yetra.service;

import de.othr.sw.yetra.entity.Order;
import de.othr.sw.yetra.entity.OrderStatus;
import de.othr.sw.yetra.entity.OrderType;
import de.othr.sw.yetra.entity.User;
import de.othr.sw.yetra.repo.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

@Service
public class OrderService implements OrderServiceIF {
    @Autowired
    OrderRepository orderRepo;

    @Autowired
    ShareServiceIF shareService;

    @Autowired
    TransactionServiceIF transactionService;

    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public Order createOrder(Order order) throws ServiceException {
        if (orderRepo.existsById(order.getId()))
            throw new ServiceException(409, "Order already exists");

        // throws exception if share does not exist
        shareService.getShare(order.getShare().getIsin());

        order.setStatus(OrderStatus.OPEN);
        order.setDate(new Date());

        order = orderRepo.save(order);

        Optional<Order> matchingOrder = this.findMatchingOrder(order);
        if (matchingOrder.isPresent()) {
            transactionService.createTransaction(
                    order.getType() == OrderType.BUY ? order : matchingOrder.get(),
                    order.getType() == OrderType.BUY ? matchingOrder.get() : order
            );
            order.setStatus(OrderStatus.CLOSED);
            matchingOrder.get().setStatus(OrderStatus.CLOSED);
            order.getShare().setCurrentPrice(order.getUnitPrice());

            //TODO: geld ueberweisen
            //falls error -> rollback changes
            //TODO: falls trading partner, trade gebühr verlangen
            //TODO: notify via jms channel
        }

        return order;
    }

    @Override
    public Order getOrder(long id) throws ServiceException {
        return orderRepo
                .findById(id)
                .orElseThrow(() -> {
                    throw new ServiceException(404, "Order not found");
                });
    }

    @Override
    public Order getOrder(long id, User user) throws ServiceException {
        return orderRepo
                .findOrderByIdAndClient(id, user)
                .orElseThrow(() -> {
                    throw new ServiceException(404, "Order not found");
                });
    }

    @Override
    public Iterable<Order> getOrders() {
        return orderRepo.findAll();
    }

    @Override
    public Iterable<Order> getOrders(User user) {
        return orderRepo.findOrdersByClient(user);
    }

    private Optional<Order> findMatchingOrder(Order order) {
        return orderRepo.findFirstByStatusAndTypeAndShareAndQuantityAndUnitPriceOrderByDateAsc(
                OrderStatus.OPEN,
                order.getType() == OrderType.BUY ? OrderType.SELL : OrderType.BUY,
                order.getShare(),
                order.getQuantity(),
                order.getUnitPrice()
            );
    }
}
