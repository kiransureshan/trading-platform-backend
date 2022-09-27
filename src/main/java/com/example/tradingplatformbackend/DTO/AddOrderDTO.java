package com.example.tradingplatformbackend.DTO;

import com.example.tradingplatformbackend.Models.TradeSide;

public class AddOrderDTO {

    public String ticker;
    public Integer numShares;
    public TradeSide side;
    public double cost;

    public AddOrderDTO(String ticker, Integer numShares, TradeSide side, double cost){
        this.ticker = ticker;
        this.numShares = numShares;
        this.side = side;
        this.cost = cost;
    }

    public String getTicker() {
        return ticker;
    }

    public Integer getNumShares() {
        return numShares;
    }

    public TradeSide getSide() {
        return side;
    }

    public double getCost() {
        return cost;
    }


}
