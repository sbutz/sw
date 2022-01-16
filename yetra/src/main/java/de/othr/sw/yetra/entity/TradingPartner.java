package de.othr.sw.yetra.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class TradingPartner extends User {
    @Embedded
    @NotNull
    @Valid
    private BankAccount billingBankAccount;

    @NotBlank
    private String notifyChannelName;

    public TradingPartner() {
    }

    public BankAccount getBillingBankAccount() {
        return billingBankAccount;
    }

    public void setBillingBankAccount(BankAccount billingBankAccount) {
        this.billingBankAccount = billingBankAccount;
    }

    public String getNotifyChannelName() {
        return notifyChannelName;
    }

    public void setNotifyChannelName(String notifyChannelName) {
        this.notifyChannelName = notifyChannelName;
    }
}
