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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.SortedMap;
import java.util.TreeMap;

@Controller
@RequestMapping(path = "/orders")
public class OrderController {
    @Autowired
    OrderServiceIF orderService;

    @Autowired
    ShareServiceIF shareService;

    @Autowired
    DTOEntityMapper<Order,OrderDTO> dtoMapper;

    @GetMapping("")
    public String getOrders(Model model,
                            @AuthenticationPrincipal User user,
                            @RequestParam(value = "page", required = false, defaultValue = "0") int page)
    {
        //TODO: sort by date descending
        //TODO: use OrderDTO
        //TODO: page size as RequestParam or @Value everywhere
        model.addAttribute("orders", orderService.getOrders(user, PageRequest.of(page, 20)));
        return "orderList";
    }

    @GetMapping("/create")
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

    @PostMapping("/create")
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
            orderService.createOrder(o);
            return "redirect:/orders";
        }
    }
}
