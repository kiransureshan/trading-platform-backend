package com.example.tradingplatformbackend.DTO;

import java.util.UUID;

public class AddTickerDTO {

    public UUID wlId;
    public String ticker;

    public AddTickerDTO(String ticker, UUID wlId){
        this.wlId = wlId;
        this.ticker = ticker;
    }
}
