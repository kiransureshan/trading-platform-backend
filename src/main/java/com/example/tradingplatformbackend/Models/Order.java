package com.example.tradingplatformbackend.Models;

import javax.persistence.*;
import java.util.UUID;

@Entity(name = "Order")
@Table(name = "orders")
public class Order {
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
            nullable = false,
            columnDefinition = "INTEGER"
    )
    private final Integer numShares;
    @Column(
            nullable = false
    )
    private final TradeSide tradeSide;
    @Column(
            name = "time",
            nullable = false,
            columnDefinition = "DOUBLE PRECISION"
    )
    private final Long time;

    @Column(
            name = "cost",
            nullable = false,
            columnDefinition = "DOUBLE PRECISION"
    )
    private final double cost;

    public Order(String ticker, Integer numShares, TradeSide side, double cost){
        this.ticker = ticker;
        this.numShares = numShares;
        this.tradeSide = side;
        this.time = System.currentTimeMillis();
        this.cost = cost;
    }

    public Order() {
        this.ticker = "";
        this.numShares = 0;
        this.tradeSide = TradeSide.BUY;
        this.time = System.currentTimeMillis();
        this.cost = 0;
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
