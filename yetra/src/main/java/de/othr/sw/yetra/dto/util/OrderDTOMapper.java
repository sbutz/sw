package de.othr.sw.yetra.dto.util;

import de.othr.sw.yetra.dto.OrderDTO;
import de.othr.sw.yetra.entity.BankAccount;
import de.othr.sw.yetra.entity.Order;
import de.othr.sw.yetra.service.ShareServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

@Component
@Scope(SCOPE_SINGLETON)
public class OrderDTOMapper implements DTOMapper<Order, OrderDTO> {

    @Autowired
    ShareServiceIF shareService;

    @Override
    public OrderDTO toDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setType(order.getType());
        dto.setIsin(order.getShare().getIsin());
        dto.setQuantity(order.getQuantity());
        dto.setUnitPrice(order.getUnitPrice());
        dto.setStatus(order.getStatus());
        dto.setTimestamp(order.getTimestamp());
        dto.setIban(order.getBankAccount().getIban());
        return dto;
    }

    @Override
    public Order fromDTO(OrderDTO dto) {
        Order order = new Order();
        order.setId(dto.getId());
        order.setType(dto.getType());
        order.setShare(shareService.getShare(dto.getIsin()));
        order.setQuantity(dto.getQuantity());
        order.setUnitPrice(dto.getUnitPrice());
        order.setStatus(dto.getStatus());
        order.setTimestamp(dto.getTimestamp());
        order.setBankAccount(new BankAccount(dto.getIban()));
        return order;
    }
}
