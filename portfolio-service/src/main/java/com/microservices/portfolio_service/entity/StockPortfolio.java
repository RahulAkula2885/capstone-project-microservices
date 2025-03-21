package com.microservices.portfolio_service.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
@Table(name="STOCK_TRADE_DETAILS")
public class StockPortfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Long ID;

    @Column(name="Stock_Code")
    private String Stock_Code;

    @Column(name="Stock_name")
    private String Stock_name;

    @Column(name="Day_Open")
    private double Day_Open;

    @Column(name="Day_High")
    private double Day_High;

    @Column(name="Day_Low")
    private double Day_Low;

    @Column(name="Traded_Price")
    private double Traded_Price;

    @Column(name="Previous_Close")
    private double Previous_Close;

    @Column(name ="traded_date_time")
    private Instant traded_date_time;
}
