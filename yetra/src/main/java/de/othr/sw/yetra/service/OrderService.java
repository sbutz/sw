package de.othr.sw.yetra.service;

import de.othr.sw.yetra.dto.DTOEntityMapper;
import de.othr.sw.yetra.dto.OrderDTO;
import de.othr.sw.yetra.entity.*;
import de.othr.sw.yetra.repo.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OrderService implements OrderServiceIF {
    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private ShareServiceIF shareService;

    @Autowired
    private TransactionServiceIF transactionService;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private DTOEntityMapper<Order, OrderDTO> dtoMapper;

    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public Order createOrder(Order order) throws ServiceException {
        if (orderRepo.existsById(order.getId()))
            throw new ServiceException(409, "Order already exists");

        // throws exception if share does not exist
        shareService.getShare(order.getShare().getIsin());

        order.setStatus(OrderStatus.OPEN);
        order.setTimestamp(LocalDateTime.now());

        order = orderRepo.save(order);

        Optional<Order> o = this.findMatchingOrder(order);
        if (o.isPresent()) {
            Order matchingOrder = o.get();
            transactionService.createTransaction(
                    order.getType() == OrderType.BUY ? order : matchingOrder,
                    order.getType() == OrderType.BUY ? matchingOrder : order
            );
            order.setStatus(OrderStatus.CLOSED);
            matchingOrder.setStatus(OrderStatus.CLOSED);
            order.getShare().setCurrentPrice(order.getUnitPrice());

            //TODO: geld ueberweisen
            //TODO: falls trading partner, trade gebühr verlangen
            //falls error -> abort and rollback changes

            if (order.getClient() instanceof TradingPartner partner && partner.getNotifyChannelName() != null)
                    jmsTemplate.convertAndSend(partner.getNotifyChannelName(), dtoMapper.toDTO(order));
            if (matchingOrder.getClient() instanceof TradingPartner partner && partner.getNotifyChannelName() != null)
                jmsTemplate.convertAndSend(partner.getNotifyChannelName(), dtoMapper.toDTO(matchingOrder));
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
    public Page<Order> getOrders(Pageable pageable) {
        return orderRepo.findAll(pageable);
    }

    @Override
    public Page<Order> getOrders(User user, Pageable pageable) {
        return orderRepo.findOrdersByClientOrderByTimestampDesc(user, pageable);
    }

    private Optional<Order> findMatchingOrder(Order order) {
        return orderRepo.findFirstByStatusAndTypeAndShareAndQuantityAndUnitPriceOrderByTimestampAsc(
                OrderStatus.OPEN,
                order.getType() == OrderType.BUY ? OrderType.SELL : OrderType.BUY,
                order.getShare(),
                order.getQuantity(),
                order.getUnitPrice()
            );
    }
}
