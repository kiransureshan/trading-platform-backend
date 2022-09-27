package com.example.tradingplatformbackend.Repositories;

import java.util.UUID;
import com.example.tradingplatformbackend.Models.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WatchlistRepo extends JpaRepository<Watchlist, UUID> { }
