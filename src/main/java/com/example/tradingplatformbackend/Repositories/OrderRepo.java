package com.example.tradingplatformbackend.Repositories;

import com.example.tradingplatformbackend.Models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepo extends JpaRepository<Order, UUID> { }
