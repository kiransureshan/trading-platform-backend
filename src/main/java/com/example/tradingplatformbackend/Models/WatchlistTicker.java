package com.example.tradingplatformbackend.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "WATCHLIST_TICKERS")
public class WatchlistTicker {
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    @Column
    private UUID id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "watchlist_id", nullable = false)
    private Watchlist watchlist;

    @Column
    private String ticker;

    public WatchlistTicker(){
    }

    public WatchlistTicker(Watchlist wl, String ticker){
        this.ticker = ticker;
        this.watchlist = wl;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Watchlist getWatchlist() {
        return watchlist;
    }

    public void setWatchlist(Watchlist watchlist) {
        this.watchlist = watchlist;
    }
}
