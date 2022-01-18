package de.othr.sw.yetra.entity;

import de.othr.sw.yetra.entity.util.SingleIdEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

@Entity
public class Transaction extends SingleIdEntity<Long> {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @PastOrPresent(message = "Timestamp must not be in the future.")
    private LocalDateTime timestamp;

    @NotNull
    @PositiveOrZero(message = "Price must be greater or equal zero.")
    private double unitPrice;

    @NotNull
    @ManyToOne()
    private Share share;

    @NotNull
    @OneToOne(orphanRemoval = false)
    private Order buyOrder;

    @NotNull
    @OneToOne(orphanRemoval = false)
    private Order sellOrder;

    public Transaction() {
    }

    public Transaction(Share share, double unitPrice, Order buyOrder, Order sellOrder) {
        this.timestamp = LocalDateTime.now();
        this.unitPrice = unitPrice;
        this.share = share;
        this.buyOrder = buyOrder;
        this.sellOrder = sellOrder;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime datum) {
        this.timestamp = datum;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double preis) {
        this.unitPrice = preis;
    }

    public Share getShare() {
        return share;
    }

    public void setShare(Share share) {
        this.share = share;
    }

    public Order getBuyOrder() {
        return buyOrder;
    }

    public void setBuyOrder(Order buyOrder) {
        this.buyOrder = buyOrder;
    }

    public Order getSellOrder() {
        return sellOrder;
    }

    public void setSellOrder(Order sellOrder) {
        this.sellOrder = sellOrder;
    }
}
