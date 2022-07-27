package com.example.tradingplatformbackend.Models;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Watchlist {
    private final UUID id;
    private String name;
    private final HashSet<String> tickers;

    public Watchlist(String name){
        this.id = UUID.randomUUID();
        this.name = name;
        this.tickers = new HashSet<String>();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String newName){
        this.name = newName;
    }

    public HashSet<String> getTickers() {
        return new HashSet<String>(this.tickers);
    }

    public void addTicker(String newTicker){
        this.tickers.add(newTicker);
    }

    public void removeTicker(String ticker){
        this.tickers.remove(ticker);
    }
}
