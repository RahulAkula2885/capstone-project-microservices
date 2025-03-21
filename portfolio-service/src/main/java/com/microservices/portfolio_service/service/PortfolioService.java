package com.microservices.portfolio_service.service;

import com.microservices.portfolio_service.entity.StockPortfolio;
import com.microservices.portfolio_service.exceptions.CustomException;
import com.microservices.portfolio_service.repository.PortfolioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;

    public void addStock(StockPortfolio stockPortfolio) {
        portfolioRepository.save(stockPortfolio);
    }

    public void deleteStock(Long id) {
        portfolioRepository.deleteById(id);
    }

    public void deleteAllStocks() {
        portfolioRepository.deleteAll();
    }

    public List<StockPortfolio> getAllPortfolio() {
        return portfolioRepository.findAll();
    }

    public StockPortfolio getByStockCode(String stockCode) {
        if (!StringUtils.hasText(stockCode)) {
            throw new RuntimeException("Stockcode must not be null or empty");
        }
        StockPortfolio portfolio = portfolioRepository.findByStockCode(stockCode)
                .orElseThrow(() -> new CustomException("Portfolio not found for stock code: " + stockCode));


        return portfolio;
    }
}
