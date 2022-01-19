package de.othr.sw.yetra.setup;

import com.google.common.collect.Iterables;
import de.othr.sw.yetra.entity.Order;
import de.othr.sw.yetra.entity.OrderType;
import de.othr.sw.yetra.entity.Share;
import de.othr.sw.yetra.entity.User;
import de.othr.sw.yetra.repository.OrderRepository;
import de.othr.sw.yetra.repository.ShareRepository;
import de.othr.sw.yetra.service.OrderServiceIF;
import de.othr.sw.yetra.service.ServiceException;
import de.othr.sw.yetra.util.MathUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Random;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;


@Component
@Scope(SCOPE_SINGLETON)
public class OrderBot {

    private final static int interval = 20;

    private final static int delay = 10;

    private final static Logger logger = LoggerFactory.getLogger(OrderBot.class);

    private final static Random random = new Random();

    @Autowired
    private OrderServiceIF orderService;

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private ShareRepository shareRepo;

    @Autowired
    @Qualifier("bot")
    private User bot;

    //@Scheduled(fixedDelay = interval * 1000, initialDelay = delay * 1000)
    public void completeOpenOrders() {
        try {
            for (Order order : orderRepo.findOpenOrders()) {
                Order o = new Order();
                o.setType(order.getType() == OrderType.BUY ? OrderType.SELL : OrderType.BUY);
                o.setShare(order.getShare());
                o.setQuantity(order.getQuantity());
                o.setUnitPrice(order.getUnitPrice());
                o.setClient(bot);
                orderService.createOrder(o);
            }
        } catch (ServiceException e) {
            logger.warn("Failed to complete open orders.");
            logger.warn(e.getMessage());
        }
    }

    //@Scheduled(fixedDelay = interval * 1000, initialDelay = delay * 1000)
    public void randomOrders() {
        try {
            Iterable<Share> shares = shareRepo.findAll();
            Share s = Iterables.get(shares, random.nextInt(Iterables.size(shares)));
            double price = s.getCurrentPrice()
                    + MathUtils.round(s.getCurrentPrice() * MathUtils.random(-0.02, 0.02), 3);


            Order order = new Order();
            order.setType(OrderType.SELL);
            order.setShare(s);
            order.setQuantity(1);
            order.setUnitPrice(price);
            order.setClient(bot);
            orderService.createOrder(order);
        } catch (ServiceException e) {
            logger.warn("Failed to create random orders.");
            logger.warn(e.getMessage());
        }
    }
}
