package de.othr.sw.yetra.entity;

import de.othr.sw.yetra.entity.util.SingleIdEntity;

import java.time.LocalDateTime;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Entity(name = "ShareOrder")
public class Order extends SingleIdEntity<Long> {
    @Id
    @GeneratedValue
    private long id;

    @Enumerated(EnumType.STRING)
    @NotNull
    private OrderType type;

    @ManyToOne
    @NotNull
    private Share share;

    @Positive
    private int quantity;

    @PositiveOrZero
    private float unitPrice;

    @Enumerated(EnumType.STRING)
    @NotNull
    private OrderStatus status;

    @ManyToOne
    @NotNull
    private User client;

    @NotNull
    @PastOrPresent
    private LocalDateTime timestamp;

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
        this.timestamp = LocalDateTime.now();
        this.bankAccount = bankAccount;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime datum) {
        this.timestamp = datum;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }
}
