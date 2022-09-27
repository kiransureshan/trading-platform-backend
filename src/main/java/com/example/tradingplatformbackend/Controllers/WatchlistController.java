package com.example.tradingplatformbackend.Controllers;

import com.example.tradingplatformbackend.DTO.AddTickerDTO;
import com.example.tradingplatformbackend.Models.Watchlist;
import com.example.tradingplatformbackend.Services.WatchlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class WatchlistController {
    private final WatchlistService watchlistService;

    @Autowired
    public WatchlistController(WatchlistService watchlistService){
        this.watchlistService = watchlistService;
    }

    @MessageMapping("/watchlist/add")
    @SendTo("/stream/watchlist/updatedWatchlist")
    public Watchlist addWatchlist(@Payload String name){
        return watchlistService.addWatchlist(name);
    }

    @MessageMapping("/watchlist/delete")
    public void deleteWatchlist(@Payload UUID id){
        watchlistService.deleteWatchlist(id);
    }

    @MessageMapping("/watchlist/addTicker")
    @SendTo("/stream/watchlist/updatedWatchlist")
    public Watchlist addTickerToWatchlist(@Payload AddTickerDTO dto){
        watchlistService.addTickerToWatchlist(dto.ticker, dto.wlId);
        Watchlist tmp = watchlistService.getById(dto.wlId);
        return tmp;
    }

    @MessageMapping("/watchlist/getAll")
    @SendTo("/stream/watchlist/getAll")
    public List<Watchlist> getWatchlists(){
        List<Watchlist> res = watchlistService.getWatchlists();
        for (Watchlist wl : res){
            System.out.println(wl);
        }
        return res;
    }
}
