package com.example.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Stock;

@Repository
public interface StockRepo extends JpaRepository<Stock, Long> {
    Optional<Stock> findByName(String name);
}
