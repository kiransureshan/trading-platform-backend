package com.example.tradingplatformbackend.Repositories.MockRepositories;

import com.example.tradingplatformbackend.Models.Order;
import com.example.tradingplatformbackend.Models.TradeSide;
import com.example.tradingplatformbackend.Models.Watchlist;
import com.example.tradingplatformbackend.Repositories.Repository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Component
public class MockWatchlistRepo implements Repository<Watchlist> {
    private final List<Watchlist> watchlists = new ArrayList<>();

    public MockWatchlistRepo(){
        Watchlist w1 = new Watchlist("Watchlist 1");
        Watchlist w2 = new Watchlist("Watchlist 2");
        watchlists.add(w1);
        watchlists.add(w2);
    }
    public List<Watchlist> getAll(){
        return this.watchlists;
    }

    public boolean add(Watchlist newObj) {
        this.watchlists.add(newObj);
        return true;
    }

    public Watchlist getById(UUID id){
        for(Watchlist wl : this.watchlists){
            if(wl.getId() == id){
                return wl;
            }
        }
        return null;
    }


    public boolean deleteById(UUID id){
        for(Watchlist wl : this.watchlists){
            if(wl.getId() == id){
                this.watchlists.remove(wl);
                return true;
            }
        }
        return false;
    }

    public boolean existsById(UUID id){
        for(Watchlist wl : this.watchlists){
            if(wl.getId() == id){
                return true;
            }
        }
        return false;
    }
}
