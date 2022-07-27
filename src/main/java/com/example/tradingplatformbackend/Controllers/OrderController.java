package com.example.tradingplatformbackend.Controllers;

import com.example.tradingplatformbackend.Models.Order;
import com.example.tradingplatformbackend.Models.Trade;
import com.example.tradingplatformbackend.Models.Watchlist;
import com.example.tradingplatformbackend.Services.OrderService;
import com.example.tradingplatformbackend.Services.TradeService;
import com.example.tradingplatformbackend.Services.WatchlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class OrderController {
    private final OrderService orderService;
    private final TradeService tradeService;

    @Autowired
    public OrderController(OrderService orderService, TradeService tradeService){
        this.orderService = orderService;
        this.tradeService = tradeService;
    }

    @MessageMapping("/orders/add")
    @SendTo("/stream")
    public void addOrder(@Payload Order order){
        orderService.newOrder(order.getTicker(),order.getNumShares(),order.getTradeSide(),order.getCost());
    }


    @MessageMapping("/orders/getAll")
    @SendTo("/stream")
    public List<Order> getOrders(){
        return orderService.getAllOrders();
    }
}
