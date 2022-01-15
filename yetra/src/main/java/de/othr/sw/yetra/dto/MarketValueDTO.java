package de.othr.sw.yetra.dto;

import java.time.LocalDateTime;

public class MarketValueDTO {
    private LocalDateTime timestamp;

    private float unitPrice;

    public MarketValueDTO(LocalDateTime timestamp, float unitPrice) {
        this.timestamp = timestamp;
        this.unitPrice = unitPrice;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }
}
