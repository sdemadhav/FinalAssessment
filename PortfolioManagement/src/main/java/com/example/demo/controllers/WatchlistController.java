package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.StockDto;
import com.example.demo.dto.WatchlistRequest;
import com.example.demo.services.WatchlistService;

@RestController
@RequestMapping("/watchlist")
@CrossOrigin("*")
public class WatchlistController {

    @Autowired
    private WatchlistService watchlistService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<StockDto>> getUserWatchlist(@PathVariable int userId) {
        return ResponseEntity.ok(watchlistService.getUserWatchlist(userId));
    }

    @PostMapping("/add")
    public ResponseEntity<String> addToWatchlist(@RequestBody WatchlistRequest request) {
        StockDto stockDto = new StockDto(request.getStockId(), request.getStockName(), request.getStockSymbol());
        String result = watchlistService.addStockToWatchlist(request.getUserId(), stockDto);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/remove/{userId}/{stockId}")
    public ResponseEntity<String> removeFromWatchlist(@PathVariable int userId, @PathVariable Long stockId) {
        String result = watchlistService.removeStockFromWatchlist(userId, stockId);
        return ResponseEntity.ok(result);
    }
}
