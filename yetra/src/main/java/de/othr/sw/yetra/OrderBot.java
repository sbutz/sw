package de.othr.sw.yetra;

import de.othr.sw.yetra.entity.BankAccount;
import de.othr.sw.yetra.entity.Employee;
import de.othr.sw.yetra.entity.Order;
import de.othr.sw.yetra.entity.OrderType;
import de.othr.sw.yetra.repo.OrderRepository;
import de.othr.sw.yetra.repo.UserRepository;
import de.othr.sw.yetra.service.OrderServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
//TODO: create sepearte bot user
public class OrderBot {

    @Autowired
    private UserRepository userRepository;

    //TODO: implement getOrderByStatus in service?
    @Autowired
    private OrderServiceIF orderService;
    
    @Autowired
    private OrderRepository orderRepository;

    @Scheduled(fixedDelay = 3*1000, initialDelay = 5*1000)
    public void completeOpenOrders() {
        for (Order order : orderRepository.findOpenOrders()) {
            Order o = new Order();
            o.setType(order.getType() == OrderType.BUY ? OrderType.SELL : OrderType.BUY);
            o.setShare(order.getShare());
            o.setQuantity(order.getQuantity());
            o.setUnitPrice(order.getUnitPrice());
            //TODO: inject value
            o.setBankAccount(new BankAccount("DE0123456789"));
            o.setClient(userRepository.findUserByUsername("admin").get());
            orderService.createOrder(o);
        }
    }
}
