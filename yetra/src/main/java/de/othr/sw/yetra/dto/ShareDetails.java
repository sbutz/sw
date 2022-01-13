package de.othr.sw.yetra.dto;

import de.othr.sw.yetra.entity.Share;

import java.util.Collection;

public class ShareDetails {
    private String isin;

    private String name;

    private float currentPrice;

    private Collection<MarketValue> marketValues;

    public ShareDetails(Share share, Collection<MarketValue> marketValues) {
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

    public float getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(float currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Collection<MarketValue> getMarketValues() {
        return marketValues;
    }

    public void setMarketValues(Collection<MarketValue> marketValues) {
        this.marketValues = marketValues;
    }
}
