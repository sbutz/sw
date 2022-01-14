package de.othr.sw.yetra.service;

import de.othr.sw.yetra.entity.Order;
import de.othr.sw.yetra.entity.User;

public interface OrderServiceIF {
    Order createOrder(Order order);

    Order getOrder(long id) throws ServiceException;

    Order getOrder(long id, User user) throws ServiceException;

    Iterable<Order> getOrders();

    Iterable<Order> getOrders(User user);
}