package com.example.tradingplatformbackend.Controllers;

import com.example.tradingplatformbackend.Models.Trade;
import com.example.tradingplatformbackend.Services.OrderService;
import com.example.tradingplatformbackend.Services.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class TradesController {
    private final TradeService tradeService;

    @Autowired
    public TradesController(TradeService tradeService){
        this.tradeService = tradeService;
    }

    @MessageMapping("/trades/getAllOpen")
    @SendTo("/stream/trades/getAllOpen")
    public List<Trade> getTrades(){
        return tradeService.getOpenTrades();
    }
}
