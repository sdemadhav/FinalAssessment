package com.example.demo.controllers;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.TradeRequest;
import com.example.demo.entities.Portfolio;
import com.example.demo.services.PortfolioService;

@RestController
@RequestMapping("/holdings")
@CrossOrigin("*")
public class PortfolioController {

	@Autowired
    private PortfolioService portfolioService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<Portfolio>> getUserPortfolio(@PathVariable int userId) {
        return ResponseEntity.ok(portfolioService.getUserHoldings(userId));
    }

    @PostMapping("/buy")
    public ResponseEntity<String> buyStock(@RequestBody TradeRequest request) {
    	System.out.println(request);
        return portfolioService.buyStock(request.getUserId(), request.getStockId(), request.getQuantity(), request.getCurrentPrice(),request.getStockName());
    }

    @PostMapping("/sell")
    public ResponseEntity<String> sellStock(@RequestBody TradeRequest request) {
        return portfolioService.sellStock(request.getUserId(), request.getStockId(), request.getQuantity(), request.getCurrentPrice());
        
    }

}
