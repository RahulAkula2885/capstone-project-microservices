package com.microservices.portfolio_service.controller;

import com.microservices.portfolio_service.commons.response.BaseResponse;
import com.microservices.portfolio_service.entity.StockPortfolio;
import com.microservices.portfolio_service.exceptions.CustomException;
import com.microservices.portfolio_service.exceptions.GlobalExceptionHandling;
import com.microservices.portfolio_service.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequestMapping("/app/portfolio")
@RequiredArgsConstructor
public class PortfolioController {

    private final GlobalExceptionHandling globalExceptionHandling;
    private final PortfolioService portfolioService;

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<BaseResponse> createCustomException(CustomException ex) {
        return globalExceptionHandling.createBaseResponse(INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @GetMapping
    public String welcomeMessage(@RequestParam("name") String name) {
        return String.format("Hello %s", name);
    }

    @PostMapping("/add")
    public void addStock(@RequestBody StockPortfolio stockPortfolio) {
        portfolioService.addStock(stockPortfolio);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteStock(@PathVariable Long id) {
        portfolioService.deleteStock(id);
    }

    @DeleteMapping("/deleteAll")
    public void deleteAllStocks() {
        portfolioService.deleteAllStocks();
    }

    @GetMapping("/all")
    public List<StockPortfolio> getAllPortfolio() {
        return portfolioService.getAllPortfolio();
    }

    @GetMapping("/{stockCode}")
    public StockPortfolio getByStockCode(@PathVariable String stockCode) {
       return portfolioService.getByStockCode(stockCode);
    }

}
