package com.microservices.portfolio_service.repository;

import com.microservices.portfolio_service.entity.StockPortfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PortfolioRepository extends JpaRepository<StockPortfolio,Long> {

    @Query("SELECT o from StockPortfolio o where o.Stock_Code =?1")
    Optional<StockPortfolio> findByStockCode(String stockCode);
}
