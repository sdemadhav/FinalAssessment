package com.example.demo.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "watchlist")
public class Watchlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "stock_id", nullable = false)
    private Long stockId;

    @Column(name = "stock_name", nullable = false)
    private String stockName;

    @Column(name = "stock_symbol")
    private String stockSymbol;

    public Watchlist() {}

    public Watchlist(Integer userId, Long stockId, String stockName, String stockSymbol) {
        this.userId = userId;
        this.stockId = stockId;
        this.stockName = stockName;
        this.stockSymbol = stockSymbol;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }
}
