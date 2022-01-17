package de.othr.sw.yetra.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import de.othr.sw.yetra.entity.OrderStatus;
import de.othr.sw.yetra.entity.OrderType;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDateTime;

//TODO: replace with records?
//make fields final/immutable
//use constructor to construct from order
public class OrderDTO implements Serializable {

    private long id;

    @NotNull
    private OrderType type;

    @NotBlank
    @Size(min = 12, max = 12)
    private String isin;

    @Positive
    private int quantity;

    @PositiveOrZero
    private double unitPrice;

    private OrderStatus status;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    public OrderDTO() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public OrderType getType() {
        return type;
    }

    public void setType(OrderType type) {
        this.type = type;
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
