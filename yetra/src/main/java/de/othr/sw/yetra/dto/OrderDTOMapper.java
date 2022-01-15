package de.othr.sw.yetra.dto;

import de.othr.sw.yetra.entity.BankAccount;
import de.othr.sw.yetra.entity.Order;
import de.othr.sw.yetra.service.ShareServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderDTOMapper implements DTOEntityMapper<Order, OrderDTO> {
    @Autowired
    ShareServiceIF shareService;

    @Override
    public OrderDTO toDTO(Order o) {
        OrderDTO order = new OrderDTO();
        order.setId(o.getId());
        order.setType(o.getType());
        order.setIsin(o.getShare().getIsin());
        order.setQuantity(o.getQuantity());
        order.setUnitPrice(o.getUnitPrice());
        order.setStatus(o.getStatus());
        order.setTimestamp(o.getTimestamp());
        order.setIban(o.getBankAccount().getIban());
        return order;
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
