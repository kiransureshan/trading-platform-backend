package com.example.tradingplatformbackend.Services;

import com.example.tradingplatformbackend.Models.Order;
import com.example.tradingplatformbackend.Models.TradeSide;
import com.example.tradingplatformbackend.Models.Watchlist;
import com.example.tradingplatformbackend.Repositories.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    private final Repository<Order> repo;

    public OrderService(Repository<Order> repo){
        this.repo = repo;
    }

    public List<Order> getAllOrders(){
        return repo.getAll();
    }

    public Order newOrder(String ticker, Integer numShares, TradeSide side, double cost){
        Order order = new Order( ticker,  numShares, side,cost);
        repo.add(order);
        return order;
    }



}
