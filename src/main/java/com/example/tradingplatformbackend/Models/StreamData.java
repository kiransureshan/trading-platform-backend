package com.example.tradingplatformbackend.Models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StreamData {
    public double askPrice;
    public double bidPrice;
    public String ticker;

    public Long time;

    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public StreamData(String message){
        int tickerStart = message.lastIndexOf("symbol=") + 7;
        this.ticker = message.substring(
                tickerStart,
                message.indexOf(",",tickerStart));

        int askStart = message.lastIndexOf("askPrice=") + 9;
        this.askPrice = Double.parseDouble(message.substring(
                askStart,
                message.indexOf(",",askStart)));

        int bidStart = message.lastIndexOf("bidPrice=") + 9;
        this.bidPrice = Double.parseDouble(message.substring(
                bidStart,
                message.indexOf(",",bidStart)));

        this.time = System.currentTimeMillis();
    }
}
