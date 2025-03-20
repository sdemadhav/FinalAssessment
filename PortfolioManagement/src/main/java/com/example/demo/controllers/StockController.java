package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entities.Stock;
import com.example.demo.services.StockService;

@RestController
@RequestMapping("/stocks")
public class StockController {

    @Autowired
    private StockService stockService;

    @GetMapping("/")
    public ResponseEntity<List<Stock>> getAllStocks() {
        List<Stock> stocks = stockService.getAllStocks();
        if (stocks.isEmpty()) {
            return ResponseEntity.noContent().build(); 
        }
        return ResponseEntity.ok(stocks);
    }

    @GetMapping("/{stockId}")
    public ResponseEntity<Stock> getStockById(@PathVariable Long stockId) {
        Stock stock = stockService.getStockById(stockId);
        return stock != null ? ResponseEntity.ok(stock) : ResponseEntity.notFound().build();
    }
}
