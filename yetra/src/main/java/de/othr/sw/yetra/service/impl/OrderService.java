package de.othr.sw.yetra.service.impl;

import de.othr.sw.yetra.dto.util.DTOMapper;
import de.othr.sw.yetra.dto.OrderDTO;
import de.othr.sw.yetra.entity.*;
import de.othr.sw.yetra.repository.OrderRepository;
import de.othr.sw.yetra.service.*;
import de.othr.sw.yetra.service.ServiceException;
import de.othr.sw.yetra.util.MathUtils;
import eBank.DTO.UeberweisungDTO;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private Logger logger;

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private ShareServiceIF shareService;

    @Autowired
    private UserServiceIF userService;

    @Autowired
    private TransactionServiceIF transactionService;

    @Autowired
    private BankTransferServiceIF bankTransferService;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private DTOMapper<Order, OrderDTO> dtoMapper;

    @Autowired
    private Validator validator;

    @Value("${yetra.order.fee}")
    private double orderFee;

    @Value("${yetra.bank.account}")
    private String iban;

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
            Order buyOrder = order.getType() == OrderType.BUY ? order : o.get();
            Order sellOrder = order.getType() == OrderType.SELL ? order : o.get();

            Transaction t = transactionService.createTransaction(buyOrder, sellOrder);
            buyOrder.setStatus(OrderStatus.CLOSED);
            sellOrder.setStatus(OrderStatus.CLOSED);
            order.getShare().setCurrentPrice(order.getUnitPrice());

            if (!buyOrder.getClient().getBankAccount().equals(sellOrder.getClient().getBankAccount())) {
                UeberweisungDTO buyToSell = new UeberweisungDTO(
                        buyOrder.getClient().getBankAccount().getIban(),
                        sellOrder.getClient().getBankAccount().getIban(),
                        buyOrder.getQuantity() * buyOrder.getUnitPrice(),
                        "Transaction Id: " + t.getId()
                );
                bankTransferService.transfer(buyToSell);
            }

            /*
             * If the order amount has already transferred from buyer to seller, the transaction will be committed.
             * If transferring the order fee fails, this is not a problem.
             * Just log the required the data. A admin will read the application log an perform a manual transfer.
             */
            //TODO: test if the try-catch prevent a rollback
            try {
                UeberweisungDTO orderFeeBuy = new UeberweisungDTO(
                        buyOrder.getClient().getBankAccount().getIban(),
                        iban,
                        orderFee,
                        "Order Fee for " + buyOrder.getId()
                );
                bankTransferService.transfer(orderFeeBuy);
            } catch (ServiceException e) {
                logger.error("Failed to transfer order fee for order " + buyOrder.getId());
            }
            try {
                UeberweisungDTO orderFeeSell = new UeberweisungDTO(
                        sellOrder.getClient().getBankAccount().getIban(),
                        iban,
                        orderFee,
                        "Order Fee for " + sellOrder.getId()
                );
                bankTransferService.transfer(orderFeeSell);
            } catch (ServiceException e) {
                logger.error("Failed to transfer order fee for order " + sellOrder.getId());
            }

            if (buyOrder.getClient().getNotifyChannel() != null)
                jmsTemplate.convertAndSend(buyOrder.getClient().getNotifyChannel(), dtoMapper.toDTO(buyOrder));

            if (sellOrder.getClient().getNotifyChannel() != null)
                jmsTemplate.convertAndSend(sellOrder.getClient().getNotifyChannel(), dtoMapper.toDTO(sellOrder));
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
