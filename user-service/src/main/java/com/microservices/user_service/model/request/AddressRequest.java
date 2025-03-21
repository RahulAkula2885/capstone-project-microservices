package com.microservices.user_service.model.request;

import lombok.Data;

@Data
public class AddressRequest {

    public String city;
    public String state;
    public String country;
    public String address;
    public String pinCode;
}
