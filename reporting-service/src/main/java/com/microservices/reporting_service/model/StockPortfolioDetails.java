package com.microservices.reporting_service.model;

import lombok.Data;

import java.time.Instant;

@Data
public class StockPortfolioDetails {

    private Long ID;
    private String Stock_Code;
    private String Stock_name;
    private double Day_Open;
    private double Day_High;
    private double Day_Low;
    private double Traded_Price;
    private double Previous_Close;
    private Instant traded_date_time;
}
