package com.example.tradingplatformbackend.Models;

import java.util.UUID;

public class Order {

    private final UUID id;

    private final String ticker;

    private final  Integer numShares;

    private final TradeSide tradeSide;

    private final Long time;

    private final double cost;

    public Order(String ticker, Integer numShares, TradeSide side, double cost){
        this.id = UUID.randomUUID();
        this.ticker = ticker;
        this.numShares = numShares;
        this.tradeSide = side;
        this.time = System.currentTimeMillis();
        this.cost = cost;
    }

    public UUID getId() {
        return id;
    }

    public String getTicker() {
        return ticker;
    }

    public Integer getNumShares() {
        return numShares;
    }

    public TradeSide getTradeSide() {
        return tradeSide;
    }

    public Long getTime() {
        return time;
    }

    public double getCost(){
        return cost;
    }
}
