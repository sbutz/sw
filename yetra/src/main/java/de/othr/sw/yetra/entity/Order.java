package de.othr.sw.yetra.entity;

import de.othr.sw.yetra.entity.util.SingleIdEntity;

import java.util.Date;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

@Entity
@Table(name="orders")
public class Order extends SingleIdEntity<Long> {
    @Id
    @GeneratedValue
    private long id;

    @Enumerated(EnumType.STRING)
    @NotNull
    private OrderType type;

    @ManyToOne
    private Share share;

    @NotNull
    private int quantity;

    @NotNull
    //TODO: not zero
    //@Positive
    //@Negative
    private float unitPrice;

    @Enumerated(EnumType.STRING)
    @NotNull
    private OrderStatus status;

    @ManyToOne
    private User client;

    @NotNull
    @PastOrPresent
    private Date date;

    @Embedded
    @Valid
    @NotNull
    private BankAccount bankAccount;

    public Order() {
    }

    public Order(OrderType type, Share share, int quantity, float unitPrice, User client, BankAccount bankAccount) {
        this.type = type;
        this.share = share;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.status = OrderStatus.OPEN;
        this.client = client;
        this.date = new Date();
        this.bankAccount = bankAccount;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long nr) {
        this.id = nr;
    }

    public OrderType getType() {
        return type;
    }

    public void setType(OrderType typ) {
        this.type = typ;
    }

    public Share getShare() {
        return share;
    }

    public void setShare(Share share) {
        this.share = share;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int menge) {
        this.quantity = menge;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date datum) {
        this.date = datum;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }
}
