package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.PortfolioTransaction;

public interface PortfolioTransactionRepo extends JpaRepository<PortfolioTransaction, Long> {

    List<PortfolioTransaction> findByUserIdAndStockIdOrderByPurchaseTimeAsc(int userId, Long stockId);
}
