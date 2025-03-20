package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

    public String buyStock(int userId, Long stockId, int quantity, double currentPrice) {
        Userdto user = restTemplate.getForObject(USER_SERVICE_URL + "/" + userId, Userdto.class);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        double totalCost = currentPrice * quantity;
        if (user.getBalance() < totalCost) {
            return "Insufficient balance";
        }

        // Deduct balance
        user.setBalance(user.getBalance() - totalCost);
        restTemplate.put(USER_SERVICE_URL + "/" + userId, user);

        // Update portfolio
        Portfolio portfolio = portfolioRepository.findByUserIdAndStockId(userId, stockId)
                .orElse(new Portfolio(userId, stockId, 0));

        portfolio.setQuantity(portfolio.getQuantity() + quantity);
        portfolioRepository.save(portfolio);

        return "Stock purchased successfully!";
    }

    public String sellStock(int userId, Long stockId, int quantity, double currentPrice) {
        Userdto user = restTemplate.getForObject(USER_SERVICE_URL + "/" + userId, Userdto.class);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        Portfolio portfolio = portfolioRepository.findByUserIdAndStockId(userId, stockId)
                .orElseThrow(() -> new IllegalArgumentException("Stock not found in portfolio"));

        if (portfolio.getQuantity() < quantity) {
            return "Insufficient stock quantity";
        }

        double totalGain = currentPrice * quantity;

        // Add balance
        user.setBalance(user.getBalance() + totalGain);
        restTemplate.put(USER_SERVICE_URL + "/" + userId, user);

        // Update portfolio
        portfolio.setQuantity(portfolio.getQuantity() - quantity);
        if (portfolio.getQuantity() == 0) {
            portfolioRepository.delete(portfolio);
        } else {
            portfolioRepository.save(portfolio);
        }

        return "Stock sold successfully!";
    }
}
