package com.example.tradingplatformbackend.Models;

import javax.persistence.*;
import java.util.UUID;

@Entity(name = "Trade")
@Table(name = "trades")
public class Trade {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    @Column(
            name="id",
            updatable = false
    )
    private UUID id;

    @Column(
            name = "ticker",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private final String ticker;

    @Column(
            name = "open_time",
            nullable = false,
            columnDefinition = "DOUBLE PRECISION"
    )
    private final Long openTimeMilli;
    @Column(
            name = "is_closed",
            nullable = false,
            columnDefinition = "BOOLEAN"
    )
    private boolean closed;

    @Column(
            name = "num_shares",
            nullable = false,
            columnDefinition = "INTEGER"
    )
    private Integer numShares;

    @Column(
            name = "average_cost",
            nullable = false,
            columnDefinition = "DOUBLE PRECISION"
    )
    private double averageCost;

    public Trade(String ticker, Integer numShares, double cost){
        this.ticker = ticker;
        this.openTimeMilli = System.currentTimeMillis();
        this.numShares = numShares;
        this.averageCost = cost;
        this.closed = false;
    }

    public Trade() {
        this.ticker = "";
        this.openTimeMilli = System.currentTimeMillis();
        this.numShares = 0;
        this.averageCost = 0;
        this.closed = false;
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

    public void setNumShares(Integer shares){
        this.numShares = shares;
    }

    public double getAverageCost() {
        return averageCost;
    }

    public void setAverageCost(double newCost){
        this.averageCost = newCost;
    }
}
