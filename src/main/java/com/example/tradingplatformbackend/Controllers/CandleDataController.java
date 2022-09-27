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
    private final CandleDataApi api;

    @Autowired
    public CandleDataController(SimpMessageSendingOperations so){
        this.api = new CandleDataApi( so, "AAPL");
        Thread alpacaStream = new Thread(api);
        alpacaStream.start();
    }


    @MessageMapping("/candleData/barHistory")
    @SendTo("/stream/candleData/barHistory")
    public List<CandleBarData> getBarHistory(@Payload BarHistoryDTO dto){
        // fetch data from api and return to client
        List<CandleBarData> result = new ArrayList<>();
        try{
            api.getBarHistory(dto.getTicker(), dto.getTf()).forEach(bar -> {
                result.add(new CandleBarData(bar,dto.getTf()));
            });
        } catch (Exception e){

        }

        return result;
    }

    @MessageMapping("/candleData/changePrimaryStream")
    public void changePrimaryStream(@Payload BarHistoryDTO dto){
        api.changePrimaryStream(dto.getTicker());
    }
}
