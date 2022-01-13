package de.othr.sw.yetra.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
public class TradingPartner extends User {
    //TODO: generating keys
    //@NotBlank
    @Column(unique=true)
    //@GeneratedValue(generator = "uuid")
    private String apiKey;

    @Embedded
    @NotNull
    @Valid
    private BankAccount billingBankAccount;

    public TradingPartner() {
    }

    public TradingPartner(long nr) {
        this.id = nr;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiSchluessel) {
        this.apiKey = apiSchluessel;
    }

    public BankAccount getBillingBankAccount() {
        return billingBankAccount;
    }

    public void setBillingBankAccount(BankAccount billingBankAccount) {
        this.billingBankAccount = billingBankAccount;
    }
}
