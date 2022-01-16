package de.othr.sw.yetra.rest;

import de.othr.sw.yetra.dto.util.DTOMapper;
import de.othr.sw.yetra.dto.OrderDTO;
import de.othr.sw.yetra.entity.*;
import de.othr.sw.yetra.service.OrderServiceIF;
import de.othr.sw.yetra.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/orders")
public class OrderServiceRestController {
    @Autowired
    private OrderServiceIF orderService;

    @Autowired
    private DTOMapper<Order,OrderDTO> mapper;

    @PostMapping("")
    OrderDTO createOrder(@Valid @RequestBody OrderDTO order, @AuthenticationPrincipal User user) {
        Order o = mapper.fromDTO(order);
        o.setClient(user);
        o = orderService.createOrder(o);
        return mapper.toDTO(o);
    }

    @GetMapping("{id}")
    OrderDTO getOrder(@PathVariable(name = "id", required = true) long id,
                      @AuthenticationPrincipal User user
    ) throws ServiceException {
        return mapper.toDTO(orderService.getOrder(id, user));
    };
}
