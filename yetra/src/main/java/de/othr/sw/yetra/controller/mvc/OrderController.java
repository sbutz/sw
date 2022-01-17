package de.othr.sw.yetra.controller.mvc;

import de.othr.sw.yetra.dto.util.DTOMapper;
import de.othr.sw.yetra.dto.OrderDTO;
import de.othr.sw.yetra.entity.*;
import de.othr.sw.yetra.service.OrderServiceIF;
import de.othr.sw.yetra.service.ShareServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.SortedMap;
import java.util.TreeMap;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

@Controller
@RequestMapping(path = "/orders")
@Scope(SCOPE_SINGLETON)
public class OrderController {

    @Autowired
    private OrderServiceIF orderService;

    @Autowired
    private ShareServiceIF shareService;

    @Autowired
    private DTOMapper<Order,OrderDTO> dtoMapper;

    @Autowired
    @Qualifier("pageSize")
    private int pageSize;

    @GetMapping("")
    @PreAuthorize("hasAuthority('ORDERS_READ')")
    public String getOrders(Model model,
                            @AuthenticationPrincipal User user,
                            @RequestParam(value = "page", required = false, defaultValue = "0") int page)
    {
        model.addAttribute("orders", orderService.getOrders(user, PageRequest.of(page, pageSize)));
        return "orderTable";
    }

    @GetMapping("/create")
    @PreAuthorize("hasAuthority('ORDERS_WRITE')")
    public String getOrderForm(Model model) {
        OrderDTO order = new OrderDTO();
        order.setQuantity(1);
        SortedMap<String, String> shares = new TreeMap<>();
        for (Share s : shareService.getShares(Pageable.unpaged()))
            shares.put(s.getIsin(), s.getName() + " (" + s.getIsin() + ")");

        model.addAttribute("order", order);
        model.addAttribute("shares", shares);
        return "orderForm";
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ORDERS_WRITE')")
    public String createOrder(@ModelAttribute("order") OrderDTO order,
                              @AuthenticationPrincipal User user
    ) {
        Order o = dtoMapper.fromDTO(order);
        o.setClient(user);
        orderService.createOrder(o);
        return "redirect:/orders";
    }
}
