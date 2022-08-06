package com.example.tradingplatformbackend.Controllers;


import com.example.tradingplatformbackend.DTO.BarHistoryDTO;
import com.example.tradingplatformbackend.DataApi.CandleDataApi;
import com.example.tradingplatformbackend.Models.CandleBarData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CandleDataController {

    private final SimpMessageSendingOperations sendingOperations;
    private CandleDataApi api;
    private final String defaultTicker;

    @Autowired
    public CandleDataController(SimpMessageSendingOperations so){
        this.sendingOperations = so;
        this.defaultTicker = "BTCUSD";
        this.api = new CandleDataApi( sendingOperations, defaultTicker);
        Thread alpacaStream = new Thread(api);
        alpacaStream.start();
    }

    public void addTickerToStream(List<String> tickers){
        api.addTickersToStream(tickers);
    }

    @MessageMapping("/candleData/barHistory")
    @SendTo("/stream/candleData/barHistory")
    public List<CandleBarData> getBarHistory(@Payload BarHistoryDTO dto){
        // fetch data from api and return to client
        List<CandleBarData> result = new ArrayList<>();
        api.getBarHistory(dto.getTicker(), dto.getTf()).forEach(bar -> {
            result.add(new CandleBarData(bar,dto.getTf()));
        });
        return result;
    }
}
