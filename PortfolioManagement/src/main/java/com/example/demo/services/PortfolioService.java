package com.example.demo.services;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.example.demo.dto.Userdto;
import com.example.demo.entities.Portfolio;
import com.example.demo.entities.PortfolioTransaction;
import com.example.demo.repositories.PortfolioRepo;
import com.example.demo.repositories.PortfolioTransactionRepo;

@Service
@Transactional
public class PortfolioService {

    @Autowired
    private PortfolioRepo portfolioRepository;
    
    @Autowired
    private PortfolioTransactionRepo portfolioTransactionRepo;

    @Autowired
    private RestTemplate restTemplate;

    private static final String USER_SERVICE_URL = "http://USERMANAGEMENT/api/users";
    
    public List<Portfolio> getUserHoldings(int userId) {
        return portfolioRepository.findByUserId(userId);
    }


    public ResponseEntity<String> buyStock(int userId, Long stockId, int quantity, double currentPrice, String stockName) {
        Userdto user = restTemplate.getForObject(USER_SERVICE_URL + "/" + userId, Userdto.class);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        double totalCost = currentPrice * quantity;
        if (user.getBalance() < totalCost) {
            return ResponseEntity.status(HttpStatusCode.valueOf(200)).body("Insufficient balance");
        }

        user.setBalance(user.getBalance() - totalCost);
        restTemplate.put(USER_SERVICE_URL + "/" + userId, user);

        Portfolio portfolio = portfolioRepository.findByUserIdAndStockId(userId, stockId)
                .orElse(new Portfolio(userId, stockId, stockName, 0, currentPrice));

        portfolio.setQuantity(portfolio.getQuantity() + quantity);
        portfolio.setCurrentPrice(currentPrice); 

        Portfolio updatedPortfolio = portfolioRepository.save(portfolio);


        PortfolioTransaction transaction = new PortfolioTransaction();
        transaction.setUserId(userId);
        transaction.setStockId(stockId);
        transaction.setQuantity(quantity);
        transaction.setPurchasePrice(currentPrice);
        transaction.setPurchaseTime(LocalDateTime.now());
        transaction.setPortfolio(updatedPortfolio);

        portfolioTransactionRepo.save(transaction);

        return ResponseEntity.ok("Stock Purchased Successfully! " + updatedPortfolio);
    }


    public ResponseEntity<String> sellStock(Long portfolioId, int userId, int quantity, double currentPrice) {
        Userdto user = restTemplate.getForObject(USER_SERVICE_URL + "/" + userId, Userdto.class);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new IllegalArgumentException("Stock not found in portfolio"));

        if (portfolio.getQuantity() < quantity) {
            return ResponseEntity.status(HttpStatusCode.valueOf(200)).body("Insufficient stock quantity");
        }

        double totalGain = 0;
        int remainingQuantity = quantity;
        

        List<PortfolioTransaction> transactions = portfolioTransactionRepo
                .findByUserIdAndStockIdOrderByPurchaseTimeAsc(userId, portfolio.getStockId());

        Iterator<PortfolioTransaction> iterator = transactions.iterator();

        while (iterator.hasNext() && remainingQuantity > 0) {
            PortfolioTransaction transaction = iterator.next();

            if (transaction.getQuantity() <= remainingQuantity) {

                totalGain += transaction.getQuantity() * (currentPrice - transaction.getPurchasePrice());
                remainingQuantity -= transaction.getQuantity();
                portfolioTransactionRepo.delete(transaction);
            } else {

                totalGain += remainingQuantity * (currentPrice - transaction.getPurchasePrice());
                transaction.setQuantity(transaction.getQuantity() - remainingQuantity);
                portfolioTransactionRepo.save(transaction);
                remainingQuantity = 0;
            }
        }


        user.setBalance(user.getBalance() + totalGain);
        restTemplate.put(USER_SERVICE_URL + "/" + userId, user);

        portfolio.setQuantity(portfolio.getQuantity() - quantity);
        if (portfolio.getQuantity() == 0) {
            portfolioRepository.delete(portfolio);
        } else {
            portfolioRepository.save(portfolio);
        }

        return ResponseEntity.ok("Stock Sold Successfully! Profit: " + totalGain);
    }

}
