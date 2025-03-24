package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.example.demo.dto.Userdto;
import com.example.demo.entities.Portfolio;
import com.example.demo.repositories.PortfolioRepo;

@Service
@Transactional
public class PortfolioService {

    @Autowired
    private PortfolioRepo portfolioRepository;

    @Autowired
    private RestTemplate restTemplate;

    private static final String USER_SERVICE_URL = "http://localhost:8081/api/users";

    public List<Portfolio> getUserHoldings(int userId) {
        return portfolioRepository.findByUserId(userId);
    }

    public ResponseEntity<String> buyStock(int userId, Long stockId, int quantity, double currentPrice, String stockName) {
        Userdto user = restTemplate.getForObject(USER_SERVICE_URL + "/" + userId, Userdto.class);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        
        System.out.println(user);

        double totalCost = currentPrice * quantity;
        if (user.getBalance() < totalCost) {
        	
            return ResponseEntity.status(HttpStatusCode.valueOf(200)).body("Insufficient balance");
        }

       
        user.setBalance(user.getBalance() - totalCost);
        restTemplate.put(USER_SERVICE_URL + "/" + userId, user);

        
        Portfolio portfolio = portfolioRepository.findByUserIdAndStockId(userId, stockId)
                .orElse(new Portfolio(userId, stockId, 0));

        portfolio.setQuantity(portfolio.getQuantity() + quantity);
        portfolio.setStockName(stockName);
        portfolio.setCurrentPrice(currentPrice);
        
        portfolioRepository.save(portfolio);

        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body("Stock Purchased Successfully!");
    }

    public ResponseEntity<String> sellStock(int userId, Long stockId, int quantity, double currentPrice) {
        Userdto user = restTemplate.getForObject(USER_SERVICE_URL + "/" + userId, Userdto.class);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        Portfolio portfolio = portfolioRepository.findByUserIdAndStockId(userId, stockId)
                .orElseThrow(() -> new IllegalArgumentException("Stock not found in portfolio"));

        System.out.println("PPPPOOOOOOORRRRTTTFOOOOLIOOOO:::::::"+portfolio.toString());
        System.out.println("QUANANANNANANNANANA::"+(portfolio.getQuantity()-quantity));
        if (portfolio.getQuantity() < quantity) {
        	System.out.println("YESSSSSSSSSSSSSSSSSSSSSS DETEETEETETETTCCCCCEDEDEDEDEDEDEDED !!!");
            return ResponseEntity.status(HttpStatusCode.valueOf(200)).body("Insufficient stock quantity");
        }

        double totalGain = currentPrice * quantity;

        
        user.setBalance(user.getBalance() + totalGain);
        restTemplate.put(USER_SERVICE_URL + "/" + userId, user);


        portfolio.setQuantity(portfolio.getQuantity() - quantity);
        if (portfolio.getQuantity() == 0) {
            portfolioRepository.delete(portfolio);
        } else {
            portfolioRepository.save(portfolio);
        }

        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body("Stock Sold Successfully!");
    }
}
