package com.example.tradingplatformbackend.Services;

import com.example.tradingplatformbackend.Models.Watchlist;
import com.example.tradingplatformbackend.Repositories.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class WatchlistService {

    private final Repository<Watchlist> repo;

    public WatchlistService(Repository<Watchlist> repo){
        this.repo = repo;
    }

    public void addWatchlist(String name){
    }

    public void deleteWatchlist(UUID id){
    }

    public void addTickerToWatchlist(String ticker, UUID watchlistId){

    }

    public List<Watchlist> getWatchlists(){
        return repo.getAll();
    }


}
