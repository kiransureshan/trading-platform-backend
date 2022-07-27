package com.example.tradingplatformbackend.Repositories.MockRepositories;

import com.example.tradingplatformbackend.Models.Trade;
import com.example.tradingplatformbackend.Repositories.Repository;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Component
public class MockTradeRepo implements Repository<Trade> {
    private final List<Trade> openTrades = new ArrayList<>(){
        {
            add(new Trade("AAPL",100,100));
            add(new Trade("TSLA",50,2000));
            add(new Trade("MSFT",200,50));
        }
    };

    public List<Trade> getAll(){
        return this.openTrades;
    }

    public boolean add(Trade newObj) {
        this.openTrades.add(newObj);
        return true;
    }

    public Trade getById(UUID id){
        for(Trade tr : this.openTrades){
            if(tr.getId() == id){
                return tr;
            }
        }
        return null;
    }


    public boolean deleteById(UUID id){
        for(Trade tr : this.openTrades){
            if(tr.getId() == id){
                this.openTrades.remove(tr);
                return true;
            }
        }
        return false;
    }

    public boolean existsById(UUID id){
        for(Trade tr : this.openTrades){
            if(tr.getId() == id){
                return true;
            }
        }
        return false;
    }
}
