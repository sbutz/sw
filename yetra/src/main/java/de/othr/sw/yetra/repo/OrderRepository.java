package de.othr.sw.yetra.repo;

import de.othr.sw.yetra.entity.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface OrderRepository extends CrudRepository<Order, Long> {

    Optional<Order> findFirstByStatusAndTypeAndShareAndQuantityAndUnitPriceOrderByTimestampAsc(
            OrderStatus status,
            OrderType type,
            Share share,
            int quantity,
            float unitPrice
    );

    Optional<Order> findOrderByIdAndClient(long id, User client);

    Iterable<Order> findOrdersByClient(User client);

    @Query("SELECT o FROM ShareOrder o WHERE o.status = de.othr.sw.yetra.entity.OrderStatus.OPEN")
    Iterable<Order> findOpenOrders();
}
