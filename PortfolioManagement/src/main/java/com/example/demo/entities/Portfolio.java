package com.example.demo.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "portfolio")
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long portfolioId;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "stock_id", nullable = false)
    private Long stockId;

    @Column(name = "stock_name" ,nullable=false)
    private String stockName;

    @Column(name = "stock_symbol")
    private String stockSymbol;

    @Column(nullable = false)
    private Integer quantity;
    
    @Column(name ="current_price", nullable=false)
    private double currentPrice;
    

    public Portfolio() {}

    public Portfolio(Long portfolioId, Integer userId, Long stockId, String stockName, String stockSymbol,
			Integer quantity, double currentPrice) {
		super();
		this.portfolioId = portfolioId;
		this.userId = userId;
		this.stockId = stockId;
		this.stockName = stockName;
		this.stockSymbol = stockSymbol;
		this.quantity = quantity;
		this.currentPrice = currentPrice;
	}
    
    public Portfolio(int userId,Long stockId, String stockName ,int quantity ,double currentPrice) {
    	super();
    	this.userId = userId;
    	this.stockId = stockId;
    	this.stockName = stockName;
    	this.quantity = quantity;
    	this.currentPrice = currentPrice;
    	
    }


	public Portfolio(Integer userId, Long stockId, Integer quantity) {
		super();
		this.userId = userId;
		this.stockId = stockId;
		this.quantity = quantity;
	}

	public Long getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(Long portfolioId) {
        this.portfolioId = portfolioId;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

	public double getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(double currentPrice) {
		this.currentPrice = currentPrice;
	}

	@Override
	public String toString() {
		return "Portfolio [portfolioId=" + portfolioId + ", userId=" + userId + ", stockId=" + stockId + ", stockName="
				+ stockName + ", stockSymbol=" + stockSymbol + ", quantity=" + quantity + ", currentPrice="
				+ currentPrice + "]";
	}
	
	
    
}
