package de.othr.sw.yetra.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class MarketValueDTO implements Serializable {
    private LocalDateTime timestamp;

    private double unitPrice;

    public MarketValueDTO() {

    }

    public MarketValueDTO(LocalDateTime timestamp, double unitPrice) {
        this.timestamp = timestamp;
        this.unitPrice = unitPrice;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
}
