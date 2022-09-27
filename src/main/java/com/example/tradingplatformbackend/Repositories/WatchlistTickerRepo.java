package com.example.tradingplatformbackend.Repositories;

import com.example.tradingplatformbackend.Models.Watchlist;
import com.example.tradingplatformbackend.Models.WatchlistTicker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WatchlistTickerRepo extends JpaRepository<WatchlistTicker, UUID> { }
