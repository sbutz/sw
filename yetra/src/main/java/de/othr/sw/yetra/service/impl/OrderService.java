package de.othr.sw.yetra.service.impl;

import de.othr.sw.yetra.dto.util.DTOMapper;
import de.othr.sw.yetra.dto.OrderDTO;
import de.othr.sw.yetra.entity.*;
import de.othr.sw.yetra.repository.OrderRepository;
import de.othr.sw.yetra.service.*;
import de.othr.sw.yetra.service.ServiceException;
import de.othr.sw.yetra.util.MathUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

@Service
@Scope(SCOPE_SINGLETON)
public class OrderService implements OrderServiceIF {

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private ShareServiceIF shareService;

    @Autowired
    private UserServiceIF userService;

    @Autowired
    private TransactionServiceIF transactionService;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private DTOMapper<Order, OrderDTO> dtoMapper;

    @Autowired
    private Validator validator;

    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public Order createOrder(Order order) throws ServiceException {
        order.setId(0);
        order.setStatus(OrderStatus.OPEN);
        order.setTimestamp(LocalDateTime.now());
        order.setUnitPrice(MathUtils.round(order.getUnitPrice(), 3));

        Set<ConstraintViolation<Order>> errors = validator.validate(order);
        if (!errors.isEmpty())
            throw new InvalidEntityException(errors.iterator().next().getMessage());

        // throws exception if share does not exist
        Share share = shareService.getShare(order.getShare().getIsin());
        order.setShare(share);

        // throws exception if user does not exist
        User user = userService.getUser(order.getClient().getId());
        order.setClient(user);

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

            if (order.getClient().getNotifyChannel() != null)
                jmsTemplate.convertAndSend(order.getClient().getNotifyChannel(), dtoMapper.toDTO(order));

            if (matchingOrder.getClient().getNotifyChannel() != null)
                jmsTemplate.convertAndSend(matchingOrder.getClient().getNotifyChannel(), dtoMapper.toDTO(matchingOrder));
        }

        return order;
    }

    @Override
    public Order getOrder(long id, User user) throws ServiceException {
        return orderRepo
                .findOrderByIdAndClient(id, user)
                .orElseThrow(() -> {
                    throw new NotFoundException("Order not found");
                });
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
