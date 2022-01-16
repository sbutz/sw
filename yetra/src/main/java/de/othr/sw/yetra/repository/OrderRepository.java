package de.othr.sw.yetra.repository;

import de.othr.sw.yetra.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {

    Optional<Order> findFirstByStatusAndTypeAndShareAndQuantityAndUnitPriceOrderByTimestampAsc(
            OrderStatus status,
            OrderType type,
            Share share,
            int quantity,
            double unitPrice
    );

    Optional<Order> findOrderByIdAndClient(long id, User client);

    Page<Order> findOrdersByClientOrderByTimestampDesc(User client, Pageable pageable);

    @Query("SELECT o FROM ShareOrder o WHERE o.status = de.othr.sw.yetra.entity.OrderStatus.OPEN")
    Iterable<Order> findOpenOrders();
}
