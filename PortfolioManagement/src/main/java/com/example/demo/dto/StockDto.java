package com.example.demo.dto;

public class StockDto {
    private Long stockId;
    private String stockName;
    private String stockSymbol;

    public StockDto() {}

    public StockDto(Long stockId, String stockName, String stockSymbol) {
        this.stockId = stockId;
        this.stockName = stockName;
        this.stockSymbol = stockSymbol;
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
