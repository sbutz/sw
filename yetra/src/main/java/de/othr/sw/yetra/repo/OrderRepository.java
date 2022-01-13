package de.othr.sw.yetra.repo;

import de.othr.sw.yetra.entity.Order;
import de.othr.sw.yetra.entity.OrderStatus;
import de.othr.sw.yetra.entity.OrderType;
import de.othr.sw.yetra.entity.Share;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface OrderRepository extends CrudRepository<Order, Long> {
    Optional<Order> findOrderByStatusAndTypeAndShareAndQuantity(
            OrderStatus status,
            OrderType type,
            Share share,
            int quantity
    );
}
