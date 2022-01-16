package de.othr.sw.yetra.ui;

import de.othr.sw.yetra.dto.DTOEntityMapper;
import de.othr.sw.yetra.dto.OrderDTO;
import de.othr.sw.yetra.entity.*;
import de.othr.sw.yetra.service.OrderServiceIF;
import de.othr.sw.yetra.service.ShareServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.SortedMap;
import java.util.TreeMap;

@Controller
public class OrderController {
    @Autowired
    OrderServiceIF orderService;

    @Autowired
    ShareServiceIF shareService;

    @Autowired
    DTOEntityMapper<Order,OrderDTO> dtoMapper;

    @GetMapping("/orders")
    public String getOrders(Model model,
                            @AuthenticationPrincipal User user,
                            @RequestParam(value = "page", required = false, defaultValue = "0") int page)
    {
        //TODO: sort by date descending
        //TODO: use OrderDTO
        //TODO: page size as RequestParam or @Value everywhere
        Pageable pageable = PageRequest.of(page, 20);
        Page<Order> orders;
        if (user.hasAuthority("ROLE_ADMIN"))
            orders = orderService.getOrders(pageable);
        else
            orders = orderService.getOrders(user, pageable);

        model.addAttribute("orders", orders);
        return "orderList";
    }

    @GetMapping("/orders/create")
    public String getOrderForm(Model model) {
        OrderDTO order = new OrderDTO();
        order.setQuantity(1);
        SortedMap<String, String> shares = new TreeMap<>();
        for (Share s : shareService.getShares(Pageable.unpaged()))
            shares.put(s.getIsin(), s.getName() + " (" + s.getIsin() + ")");

        model.addAttribute("order", order);
        model.addAttribute("shares", shares);
        model.addAttribute("validated", false);
        return "orderForm";
    }

    @PostMapping("/orders/create")
    public String createOrder(Model model,
                              @Valid @ModelAttribute("order") OrderDTO order,
                              @AuthenticationPrincipal User user,
                              BindingResult bindingResult)
    {
        if (bindingResult.hasErrors()) {
            model.addAttribute("order", order);
            model.addAttribute("validated", true);
            return "orderForm";
        } else {
            Order o = dtoMapper.fromDTO(order);
            o.setClient(user);
            //TODO: methode anders benennen
            orderService.createOrder(o);
            return "redirect:/orders";
        }
    }
}
