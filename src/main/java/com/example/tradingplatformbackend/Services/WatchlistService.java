package com.example.tradingplatformbackend.Services;

import com.example.tradingplatformbackend.Models.Watchlist;
import com.example.tradingplatformbackend.Models.WatchlistTicker;
import com.example.tradingplatformbackend.Repositories.Repository;
import com.example.tradingplatformbackend.Repositories.WatchlistRepo;
import com.example.tradingplatformbackend.Repositories.WatchlistTickerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class WatchlistService {
    private final WatchlistRepo repo;
    private final WatchlistTickerRepo tickerRepo;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    public WatchlistService(WatchlistRepo repo, WatchlistTickerRepo tickerRepo){
        this.repo = repo;
        this.tickerRepo = tickerRepo;
    }

    public Watchlist addWatchlist(String name){
        Watchlist wl = new Watchlist(name);
        repo.save(wl);
        return wl;
    }

    public void deleteWatchlist(UUID id){
        repo.deleteById(id);
    }

    public void addTickerToWatchlist(String ticker, UUID wlId){
        Watchlist wl = repo.findById(wlId).get();
        WatchlistTicker wlt = new WatchlistTicker(wl,ticker);
        tickerRepo.save(wlt);
    }

    public List<Watchlist> getWatchlists(){
        return repo.findAll();
    }

    public Watchlist getById(UUID id){
        Optional<Watchlist> tmp = repo.findById(id);
        try{
            return tmp.get();
        } catch (Exception e){
            return null;
        }
    }
}
