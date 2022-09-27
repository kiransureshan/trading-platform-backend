package com.example.tradingplatformbackend.Models;

import java.util.*;
import javax.persistence.*;

@Entity
@Table(name = "WATCHLISTS")
public class Watchlist {
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    @Column
    private UUID id;
    @Column
    private String name;
    @OneToMany(fetch= FetchType.EAGER, cascade=CascadeType.ALL, mappedBy = "watchlist")
    private Set<WatchlistTicker> tickers;
    public Watchlist(String name){
        this.name = name;
    }

    public Watchlist() {

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

    public Set<WatchlistTicker> getTickers() {
        return this.tickers;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
