package com.microservices.address_service.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressResponse {

    private Long id;
    private Long userId;
    public String city;
    public String state;
    public String country;
    public String address;
    public String pinCode;
}
