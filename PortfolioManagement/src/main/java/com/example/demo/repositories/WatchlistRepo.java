package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Watchlist;

public interface WatchlistRepo extends JpaRepository<Watchlist, Long> {
    List<Watchlist> findByUserId(int userId);
    boolean existsByUserIdAndStockId(int userId, Long stockId);
    void deleteByUserIdAndStockId(int userId, Long stockId);
}
