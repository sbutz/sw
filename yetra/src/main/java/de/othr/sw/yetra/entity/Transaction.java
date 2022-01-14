package de.othr.sw.yetra.entity;

import de.othr.sw.yetra.entity.util.SingleIdEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import javax.validation.constraints.PastOrPresent;
import java.util.Date;

@Entity
public class Transaction extends SingleIdEntity<Long> {
    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @PastOrPresent
    private Date date;

    @NotNull
    private float unitPrice;

    @NotNull
    @ManyToOne
    private Share share;

    @NotNull
    @OneToOne
    private Order buyOrder;

    @NotNull
    @OneToOne
    private Order sellOrder;

    public Transaction() {
    }

    public Transaction(Share share, float unitPrice, Order buyOrder, Order sellOrder) {
        this.date = new Date();
        this.unitPrice = unitPrice;
        this.share = share;
        this.buyOrder = buyOrder;
        this.sellOrder = sellOrder;
    }

    @Override
    public Long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date datum) {
        this.date = datum;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float preis) {
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
