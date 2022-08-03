package com.example.tradingplatformbackend.Models;

import net.jacobpeterson.alpaca.model.endpoint.marketdata.common.historical.bar.Bar;

public class CandleBarData {
    public Double open;

    public Double close;

    public Double high;

    public Double low;

    public CandleTimeFrame timeframe;

    public CandleBarData(Bar bar, CandleTimeFrame tf){
        this.open = bar.getOpen();
        this.close = bar.getClose();
        this.high = bar.getHigh();
        this.low = bar.getLow();
        this.timeframe =tf;
    }

    public CandleBarData(String message){
        this.timeframe = CandleTimeFrame.m1;

        int openStart = message.lastIndexOf("open=") + 5;
        this.open = Double.parseDouble(message.substring(
                openStart,
                message.indexOf(",",openStart)));

        int highStart = message.lastIndexOf("high=") + 5;
        this.open = Double.parseDouble(message.substring(
                highStart,
                message.indexOf(",",highStart)));

        int lowStart = message.lastIndexOf("low=") + 4;
        this.open = Double.parseDouble(message.substring(
                lowStart,
                message.indexOf(",",lowStart)));

        int closeStart = message.lastIndexOf("close=") + 6;
        this.open = Double.parseDouble(message.substring(
                closeStart,
                message.indexOf(",",closeStart)));
    }
}
