package com.example.tradingplatformbackend.Repositories.MockRepositories;

import com.example.tradingplatformbackend.Models.Order;
import com.example.tradingplatformbackend.Models.Trade;
import com.example.tradingplatformbackend.Models.TradeSide;
import com.example.tradingplatformbackend.Repositories.Repository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Component
public class MockOrderRepo implements Repository<Order> {
    private final List<Order> orders = new ArrayList(){
        {
            add(new Order("AAPL",100, TradeSide.BUY,100));
            add(new Order("TSLA",50,TradeSide.BUY,2000));
            add(new Order("MSFT",200,TradeSide.BUY,50));
        }
    };

    public List<Order> getAll(){
        return this.orders;
    }

    public boolean add(Order newObj) {
        this.orders.add(newObj);
        return true;
    }

    public Order getById(UUID id){
        for(Order or : this.orders){
            if(or.getId() == id){
                return or;
            }
        }
        return null;
    }


    public boolean deleteById(UUID id){
        for(Order or : this.orders){
            if(or.getId() == id){
                this.orders.remove(or);
                return true;
            }
        }
        return false;
    }

    public boolean existsById(UUID id){
        for(Order or : this.orders){
            if(or.getId() == id){
                return true;
            }
        }
        return false;
    }
}
