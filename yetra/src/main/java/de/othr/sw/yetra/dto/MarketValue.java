package de.othr.sw.yetra.dto;

import java.util.Date;

public class MarketValue {
    private Date timestamp;

    private float unitPrice;

    public MarketValue(Date timestamp, float unitPrice) {
        this.timestamp = timestamp;
        this.unitPrice = unitPrice;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }
}
