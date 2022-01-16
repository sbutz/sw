package de.othr.sw.yetra.dto;

import de.othr.sw.yetra.entity.Share;

import java.io.Serializable;
import java.util.Collection;

public class ShareDetailsDTO implements Serializable {
    private String isin;

    private String name;

    private double currentPrice;

    private Collection<MarketValueDTO> marketValues;

    public ShareDetailsDTO() {

    }

    public ShareDetailsDTO(Share share, Collection<MarketValueDTO> marketValues) {
        this.isin = share.getIsin();
        this.name = share.getName();
        this.currentPrice = share.getCurrentPrice();
        this.marketValues = marketValues;
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

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Collection<MarketValueDTO> getMarketValues() {
        return marketValues;
    }

    public void setMarketValues(Collection<MarketValueDTO> marketValues) {
        this.marketValues = marketValues;
    }
}
