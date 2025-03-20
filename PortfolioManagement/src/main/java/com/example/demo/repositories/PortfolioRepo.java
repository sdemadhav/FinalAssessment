package com.example.demo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entities.Portfolio;

public interface PortfolioRepo extends JpaRepository<Portfolio, Long> {
    List<Portfolio> findByUserId(int userId);
    Optional<Portfolio> findByUserIdAndStockId(int userId, Long stockId);
}
