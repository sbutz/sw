package de.othr.sw.yetra.service;

import de.othr.sw.yetra.entity.Order;
import de.othr.sw.yetra.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderServiceIF {

    Order createOrder(Order order);

    Order getOrder(long id) throws ServiceException;

    Order getOrder(long id, User user) throws ServiceException;

    Page<Order> getOrders(Pageable pageable);

    Page<Order> getOrders(User user, Pageable pageable);
}