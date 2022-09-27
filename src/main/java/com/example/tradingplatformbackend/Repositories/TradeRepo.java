package com.example.tradingplatformbackend.Repositories;

import com.example.tradingplatformbackend.Models.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TradeRepo extends JpaRepository<Trade, UUID> { }
