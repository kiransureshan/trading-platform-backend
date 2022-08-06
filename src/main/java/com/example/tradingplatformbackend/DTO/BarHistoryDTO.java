package com.example.tradingplatformbackend.DTO;

import com.example.tradingplatformbackend.Models.CandleTimeFrame;
import org.checkerframework.checker.units.qual.C;

public class BarHistoryDTO {
    private String ticker;
    private CandleTimeFrame tf;

    public String getTicker(){
        return ticker;
    }

    public CandleTimeFrame getTf(){
        return tf;
    }
}
