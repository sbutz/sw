package de.othr.sw.yetra.setup;

import de.othr.sw.yetra.entity.BankAccount;
import de.othr.sw.yetra.entity.Order;
import de.othr.sw.yetra.entity.OrderType;
import de.othr.sw.yetra.entity.User;
import de.othr.sw.yetra.repository.OrderRepository;
import de.othr.sw.yetra.repository.UserRepository;
import de.othr.sw.yetra.service.OrderServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Component
@DependsOn(Users.component)
public class OrderBot {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderServiceIF orderService;
    
    @Autowired
    private OrderRepository orderRepository;

    private User bot;

    @PostConstruct
    public void init() {
        bot = userRepository.findUserByUsername("bot").get();
    }

    @Scheduled(fixedDelay = 10*1000, initialDelay = 5*1000)
    public void completeOpenOrders() {
        for (Order order : orderRepository.findOpenOrders()) {
            Order o = new Order();
            o.setType(order.getType() == OrderType.BUY ? OrderType.SELL : OrderType.BUY);
            o.setShare(order.getShare());
            o.setQuantity(order.getQuantity());
            o.setUnitPrice(order.getUnitPrice());
            //TODO: inject value
            o.setBankAccount(new BankAccount("DE0123456789"));
            o.setClient(bot);
            orderService.createOrder(o);
        }
    }
}
