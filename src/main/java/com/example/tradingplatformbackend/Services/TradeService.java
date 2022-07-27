package com.example.tradingplatformbackend.Services;

import com.example.tradingplatformbackend.Models.Order;
import com.example.tradingplatformbackend.Models.Trade;
import com.example.tradingplatformbackend.Repositories.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TradeService {
    private final Repository<Trade> repo;

    @Autowired
    public TradeService(Repository<Trade> repo){
        this.repo = repo;
    }

    public void newTrade(Order order){
        List<Trade> trades = repo.getAll().stream().filter(tr -> Objects.equals(tr.getTicker(), order.getTicker())).toList();
        Trade trade;
        if (trades.size() == 0){
            return;
        }
        trade = trades.get(0);
        modifyAvgCost(order.getNumShares(), order.getCost(), trade);
        if(trade.getNumShares() == 0){
            repo.deleteById(trade.getId());
        }
    }

    public List<Trade> getOpenTrades(){
        return repo.getAll();
    }

    private void modifyAvgCost(Integer numShares, double cost, Trade trade) {
        // calculate new weighted average cost
        int newShareCount = trade.getNumShares() + numShares;
        float newSharesWeight = numShares/newShareCount;
        trade.setAverageCost(trade.getAverageCost()*(1-newSharesWeight) + cost*newSharesWeight);
        trade.setNumShares(newShareCount);
    }




}
