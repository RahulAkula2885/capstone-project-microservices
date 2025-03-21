package com.microservices.reporting_service.service;

import com.microservices.reporting_service.commons.exceptions.CustomException;
import com.microservices.reporting_service.model.StockPortfolioDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final RestTemplate restTemplate;

    public List<StockPortfolioDetails> generateDailyReport(String stockCode) {

        if (!StringUtils.hasText(stockCode)) {
            throw new CustomException("Stock code must not be null or empty");
        }

//        StockPortfolioDetails stockPortfolio = restTemplate.getForObject("http://localhost:9000/app/portfolio/" + stockCode, StockPortfolioDetails.class);
//        System.out.println(stockPortfolio);
        String url = "http://localhost:8765/PORTFOLIO-SERVICE/app/portfolio/details/" + stockCode;
        List<StockPortfolioDetails> stockPortfolioDetails = restTemplate.getForObject(url, List.class);
        System.out.println(stockPortfolioDetails);

        return stockPortfolioDetails;
        /*return Map.of(
                "stockCode", stockCode,
                "stockName", "HCL Technologies Ltd.",
                "currentStockPrice", 1469.80,
                "dayRange", "71,461.35 - 71,488.80",
                "percentChange", "0.80%"
        );*/
    }

    public Map<String, Object> generateWeeklyReport(String stockCode) {
        return Map.of(
                "stockCode", stockCode,
                "stockName", "HCL Technologies Ltd.",
                "currentStockPrice", 1469.80,
                "weeklyRange", "71,458.35 - 71,493.80",
                "percentChange", "0.28%"
        );
    }
}
