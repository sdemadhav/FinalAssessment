package com.example.demo.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Collections;
import com.example.demo.entities.Stock;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.http.HttpMethod;

@Service
public class StockService {
    
    private final RestTemplate restTemplate;
    private final String stockServiceUrl = "https://c0a1-125-18-187-66.ngrok-free.app/api/shares";

    public StockService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Stock getStockById(Long stockId) {
        try {
            return restTemplate.getForObject(stockServiceUrl + "/" + stockId, Stock.class);
        } catch (HttpClientErrorException.NotFound e) {
            System.out.println("Stock not found for ID: " + stockId);
            return null;
        } catch (RestClientException e) {
            System.err.println("Error fetching stock by ID: " + stockId);
            return null;
        }
    }

    public List<Stock> getAllStocks() {
        try {
            ResponseEntity<List<Stock>> response = restTemplate.exchange(
                stockServiceUrl + "/all",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Stock>>() {}
            );
            return response.getBody() != null ? response.getBody() : Collections.emptyList();
        } catch (RestClientException e) {
            System.err.println("Error fetching all stocks: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}
