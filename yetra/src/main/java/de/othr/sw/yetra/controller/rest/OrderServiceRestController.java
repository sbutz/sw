package de.othr.sw.yetra.controller.rest;

import de.othr.sw.yetra.dto.util.DTOMapper;
import de.othr.sw.yetra.dto.OrderDTO;
import de.othr.sw.yetra.entity.*;
import de.othr.sw.yetra.service.OrderServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

@RestController
@Scope(SCOPE_SINGLETON)
@RequestMapping(path = "/api/orders")
public class OrderServiceRestController {

    @Autowired
    private OrderServiceIF orderService;

    @Autowired
    private DTOMapper<Order,OrderDTO> mapper;

    @PostMapping("")
    @PreAuthorize("hasAuthority('ORDERS_WRITE')")
    OrderDTO createOrder(@RequestBody OrderDTO order,
                         @AuthenticationPrincipal User user
    ) {
        Order o = mapper.fromDTO(order);
        o.setClient(user);
        o = orderService.createOrder(o);
        return mapper.toDTO(o);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('ORDERS_READ')")
    OrderDTO getOrder(@PathVariable(name = "id") long id,
                      @AuthenticationPrincipal User user
    ) {
        return mapper.toDTO(orderService.getOrder(id, user));
    }
}
