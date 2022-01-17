package de.othr.sw.yetra.entity;

import de.othr.sw.yetra.entity.util.SingleIdEntity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.*;

@Entity
public class Share extends SingleIdEntity<String> {

    @Id
    @NotNull
    @Size(min = 12, max = 12, message = "ISIN must consist of 12 characters.")
    private String isin;

    @NotBlank(message = "Name must not be empty.")
    private String name;

    @PositiveOrZero(message = "Price must be greater or equal zero.")
    private double currentPrice;

    public Share() {

    }

    public Share(String isin, String name, double currentPrice) {
        this.isin = isin;
        this.name = name;
        this.currentPrice = currentPrice;
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double aktuellerPreis) {
        this.currentPrice = aktuellerPreis;
    }

    @Override
    protected String getId() {
        return this.isin;
    }
}