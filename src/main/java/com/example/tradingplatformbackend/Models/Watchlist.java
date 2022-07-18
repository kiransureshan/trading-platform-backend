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
        HashSet<String> tickersClone= (HashSet)this.tickers.clone();
        return tickersClone;
    }

    public void addTicker(String newTicker){
        this.tickers.add(newTicker);
    }

    public void removeTicker(String ticker){
        this.tickers.remove(ticker);
    }
}
