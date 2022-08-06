package com.example.tradingplatformbackend.Controllers;


import com.example.tradingplatformbackend.DTO.BarHistoryDTO;
import com.example.tradingplatformbackend.DataApi.CandleDataApi;
import com.example.tradingplatformbackend.Models.CandleBarData;
import com.example.tradingplatformbackend.Models.CandleTimeFrame;
import com.example.tradingplatformbackend.Models.ChatMessage;
import net.jacobpeterson.alpaca.model.endpoint.marketdata.common.historical.bar.Bar;
import net.jacobpeterson.alpaca.model.endpoint.marketdata.crypto.historical.bar.CryptoBar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CandleDataController {

    private final SimpMessageSendingOperations sendingOperations;
    private CandleDataApi api;
    private boolean apiStarted;
    private final String defaultTicker;

    @Autowired
    public CandleDataController(SimpMessageSendingOperations so){
        this.sendingOperations = so;
        this.apiStarted = false;
        this.defaultTicker = "BTCUSD";
    }

    @MessageMapping("/candleData/start")
    public void connectToDataStream(@Payload final String ticker){
        if(!isApiStarted()){
            api = new CandleDataApi( sendingOperations, ticker);
            Thread test = new Thread(api);
            test.start();
            apiStarted = true;
        }
    }

    public void addTickerToStream(List<String> tickers){
        if(!isApiStarted()){
            connectToDataStream(defaultTicker);
        }
        api.addTickersToStream(tickers);
    }

    @MessageMapping("/candleData/barHistory")
    @SendTo("/stream/candleData/barHistory")
    public List<CandleBarData> getBarHistory(@Payload BarHistoryDTO dto){
        if(!isApiStarted()){
            connectToDataStream(dto.getTicker());
        }
        // fetch data from api and return to client
        List<CandleBarData> result = new ArrayList<>();
        api.getBarHistory(dto.getTicker(), dto.getTf()).forEach(bar -> {
            result.add(new CandleBarData(bar,dto.getTf()));
        });
        return result;
    }


    private boolean isApiStarted(){
        return this.apiStarted;
    }

}
