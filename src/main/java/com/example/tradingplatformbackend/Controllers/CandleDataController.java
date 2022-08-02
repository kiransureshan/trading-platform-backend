package com.example.tradingplatformbackend.Controllers;


import com.example.tradingplatformbackend.DataApi.CandleDataApi;
import com.example.tradingplatformbackend.Models.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class CandleDataController {

    private final SimpMessageSendingOperations sendingOperations;

    private CandleDataApi candleApi;

    @Autowired
    public CandleDataController(SimpMessageSendingOperations so){
        this.sendingOperations = so;
    }

    @MessageMapping("/candleData/start")
    public void connectToDataStream(@Payload final String ticker){
        System.out.println(ticker);
        CandleDataApi api = new CandleDataApi( sendingOperations, "BTCUSD");
        Thread test = new Thread(api);
        test.start();
    }

    public void addTickerToStream(List<String> tickers){
        candleApi.addTickersToStream(tickers);
    }

}
