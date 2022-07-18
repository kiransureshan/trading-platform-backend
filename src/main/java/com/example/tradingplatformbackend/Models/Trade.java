package com.example.tradingplatformbackend.Models;

import java.util.Date;
import java.util.UUID;

public class Trade {
    private final UUID id;

    private final String ticker;

    private final Long openTimeMilli;

    private boolean closed = false;

    private Integer numShares;

    private double averageCost;

    public Trade(String ticker, Integer numShares, double cost){
        this.id = UUID.randomUUID();
        this.ticker = ticker;
        this.openTimeMilli = System.currentTimeMillis();
        this.numShares = numShares;
        this.averageCost = cost;
    }

    public UUID getId() {
        return id;
    }

    public String getTicker() {
        return ticker;
    }

    public Long getOpenTime() {
        return openTimeMilli;
    }

    public boolean isClosed() {
        return closed;
    }

    public void closeTrade() {
        this.closed = true;
    }

    public Integer getNumShares() {
        return numShares;
    }

    public void modifyShares(Integer numShares, double cost) {
        // calculate new weighted average cost
        int newShareCount = getNumShares() + numShares;
        float newSharesWeight = numShares/newShareCount;
        setAverageCost(getAverageCost()*(1-newSharesWeight) + cost*newSharesWeight);
        this.numShares = newShareCount;
    }

    public double getAverageCost() {
        return averageCost;
    }

    private void setAverageCost(double newCost){
        this.averageCost = newCost;
    }
}
