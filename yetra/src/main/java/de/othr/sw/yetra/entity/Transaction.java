package de.othr.sw.yetra.entity;

import de.othr.sw.yetra.entity.util.SingleIdEntity;
import javax.validation.constraints.NotNull;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.PastOrPresent;
import java.util.Date;

@Entity
public class Transaction extends SingleIdEntity<Long> {
    @Id
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

    public Transaction(float unitPrice, Share share, Order buyOrder, Order sellOrder) {
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

    public void setId(Long nr) {
        this.id = nr;
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
