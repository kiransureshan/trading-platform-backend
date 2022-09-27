package com.example.tradingplatformbackend.Services;

import com.example.tradingplatformbackend.Models.Order;
import com.example.tradingplatformbackend.Models.Trade;
import com.example.tradingplatformbackend.Repositories.Repository;
import com.example.tradingplatformbackend.Repositories.TradeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TradeService {
    private final TradeRepo repo;

    @Autowired
    public TradeService(TradeRepo repo){
        this.repo = repo;
    }

    public Trade newTrade(Order order){
        List<Trade> trades = repo.findAll().stream().filter(tr -> Objects.equals(tr.getTicker(), order.getTicker())).collect(Collectors.toList());
        if (trades.size() == 0){
            Trade newTrade = new Trade(order.getTicker(),order.getNumShares(),order.getCost());
            repo.save(newTrade);
            return newTrade;
        }
        Trade trade = trades.get(0);
        modifyAvgCost(order.getNumShares(), order.getCost(), trade);
        if(trade.getNumShares() == 0){
            repo.deleteById(trade.getId());
        }
        return trade;
    }

    public List<Trade> getOpenTrades(){
        return repo.findAll();
    }

    private void modifyAvgCost(Integer numShares, double cost, Trade trade) {
        // calculate new weighted average cost
        int newShareCount = trade.getNumShares() + numShares;
        float newSharesWeight = numShares/newShareCount;
        trade.setAverageCost(trade.getAverageCost()*(1-newSharesWeight) + cost*newSharesWeight);
        trade.setNumShares(newShareCount);
    }




}
