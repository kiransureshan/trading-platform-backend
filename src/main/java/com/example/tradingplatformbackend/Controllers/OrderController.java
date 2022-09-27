package com.example.tradingplatformbackend.Controllers;

import com.example.tradingplatformbackend.DTO.AddOrderDTO;
import com.example.tradingplatformbackend.Models.Order;
import com.example.tradingplatformbackend.Models.Trade;
import com.example.tradingplatformbackend.Services.OrderService;
import com.example.tradingplatformbackend.Services.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.List;

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
    @SendTo("/stream/orders/orderStream")
    public Trade addOrder(@Payload AddOrderDTO order){
        Order newOrder = orderService.newOrder(order.getTicker(),order.getNumShares(),order.getSide(),order.getCost());
        return tradeService.newTrade(newOrder);
    }


    @MessageMapping("/orders/getAll")
    @SendTo("/stream/orders/getAll")
    public List<Order> getOrders(){
        return orderService.getAllOrders();
    }
}
