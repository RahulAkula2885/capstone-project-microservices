package com.microservices.user_service.service;

import com.microservices.user_service.model.response.AddressResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.util.List;

@FeignClient(name = "ADDRESS-SERVICE", path = "/v1/address")
public interface AddressFeignClient {

    @GetMapping("/details/{userId}")
    public List<AddressResponse> getAddressDetailsByUserId(@PathVariable("userId") Long userId);
    //public List<AddressResponse> getAddressDetailsByEmployeeId(@PathVariable("employeeId") Long employeeId);


}