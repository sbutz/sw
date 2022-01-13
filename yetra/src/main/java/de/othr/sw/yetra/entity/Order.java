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
    private User auftraggeber;

    @NotNull
    @PastOrPresent
    private Date datum;

    @Embedded
    @Valid
    @NotNull
    private BankAccount clientBankAccount;

    public Order() {
    }

    public Order(OrderType type, Share share, int quantity, float unitPrice, User auftraggeber, BankAccount clientBankAccount) {
        this.type = type;
        this.share = share;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.status = OrderStatus.OPEN;
        this.auftraggeber = auftraggeber;
        this.datum = new Date();
        this.clientBankAccount = clientBankAccount;
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

    public void setUnitPrice(float stueckPreis) {
        this.unitPrice = stueckPreis;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public User getAuftraggeber() {
        return auftraggeber;
    }

    public void setAuftraggeber(User auftraggeber) {
        this.auftraggeber = auftraggeber;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public BankAccount getClientBankAccount() {
        return clientBankAccount;
    }

    public void setClientBankAccount(BankAccount clientBankAccount) {
        this.clientBankAccount = clientBankAccount;
    }
}
