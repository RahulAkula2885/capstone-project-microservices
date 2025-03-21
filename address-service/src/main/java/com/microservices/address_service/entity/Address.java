package com.microservices.address_service.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "userId")
    private Long userId;

    @Column(name = "city")
    public String city;

    @Column(name = "state")
    public String state;

    @Column(name = "country")
    public String country;

    @Column(name = "address")
    public String address;

    @Column(name = "pinCode")
    public String pinCode;

    @Column(name = "deleted")
    private boolean deleted;

    @Column(name = "createdTime")
    private Instant createdTime;

    @Column(name = "modifiedTime")
    private Instant modifiedTime;

}
