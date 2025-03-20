package com.example.demo.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.StockDto;
import com.example.demo.entities.Watchlist;
import com.example.demo.repositories.WatchlistRepo;

import jakarta.transaction.Transactional;

@Service
public class WatchlistService {

    @Autowired
    private WatchlistRepo watchlistRepository;

    public List<StockDto> getUserWatchlist(int userId) {
        return watchlistRepository.findByUserId(userId).stream()
                .map(watchlist -> new StockDto(watchlist.getStockId(), watchlist.getStockName(), watchlist.getStockSymbol()))
                .collect(Collectors.toList());
    }

    @Transactional
    public String addStockToWatchlist(int userId, StockDto stockDto) {
        if (watchlistRepository.existsByUserIdAndStockId(userId, stockDto.getStockId())) {
            return "Stock already in watchlist";
        }

        Watchlist watchlist = new Watchlist(userId, stockDto.getStockId(), stockDto.getStockName(), stockDto.getStockSymbol());
        watchlistRepository.save(watchlist);
        return "Stock added to watchlist";
    }

    @Transactional
    public String removeStockFromWatchlist(int userId, Long stockId) {
        if (!watchlistRepository.existsByUserIdAndStockId(userId, stockId)) {
            return "Stock not in watchlist";
        }

        watchlistRepository.deleteByUserIdAndStockId(userId, stockId);
        return "Stock removed from watchlist";
    }
}
