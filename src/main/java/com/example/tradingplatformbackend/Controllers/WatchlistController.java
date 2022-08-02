package com.example.tradingplatformbackend.Controllers;

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
    public void addWatchlist(@Payload String name){
        watchlistService.addWatchlist(name);
    }

    @MessageMapping("/watchlist/delete")
    public void deleteWatchlist(@Payload UUID id){
        watchlistService.deleteWatchlist(id);
    }

    @MessageMapping("/watchlist/addTicker")
    public void addTickerToWatchlist(@Payload String ticker, @Payload UUID watchlistId){
        watchlistService.addTickerToWatchlist(ticker, watchlistId);
    }

    @MessageMapping("/watchlist/getAll")
    @SendTo("/stream/watchlists/getAll")
    public List<Watchlist> getWatchlists(){
        return watchlistService.getWatchlists();
    }

    @MessageMapping("/watchlist/testEndpoint")
    @SendTo("/stream/test")
    public String testEndpoint(){
        return "yeet we received your request";
    }
}
