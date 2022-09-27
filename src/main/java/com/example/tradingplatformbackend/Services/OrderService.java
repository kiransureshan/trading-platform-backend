package com.example.tradingplatformbackend.Services;

import com.example.tradingplatformbackend.Models.Order;
import com.example.tradingplatformbackend.Models.TradeSide;
import com.example.tradingplatformbackend.Repositories.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepo repo;

    @Autowired
    public OrderService(OrderRepo repo){
        this.repo = repo;
    }

    public List<Order> getAllOrders(){
        return repo.findAll();
    }

    public Order newOrder(String ticker, Integer numShares, TradeSide side, double cost){
        Order order = new Order( ticker,  numShares, side,cost);
        repo.save(order);
        return order;
    }
}
