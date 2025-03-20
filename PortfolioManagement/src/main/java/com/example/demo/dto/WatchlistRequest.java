package com.example.demo.dto;

public class WatchlistRequest {
    private int userId;
    private Long stockId;
    private String stockName;
    private String stockSymbol;

    public WatchlistRequest() {}

    public WatchlistRequest(int userId, Long stockId, String stockName, String stockSymbol) {
        this.userId = userId;
        this.stockId = stockId;
        this.stockName = stockName;
        this.stockSymbol = stockSymbol;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
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
