package com.example.tradingplatformbackend.Models;

public class CandleBarData {
    public String open;

    public String close;

    public String high;

    public String low;

    public String date;

    public CandleTimeFrame timeframe;

    public CandleBarData(String o, String h, String l, String c, String date, CandleTimeFrame tf){
        this.open = o;
        this.high = h;
        this.low = l;
        this.close = c;
        this.date = date;
        this.timeframe = tf;
    }

}
