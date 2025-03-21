package com.microservices.address_service.service;

import com.microservices.address_service.commons.response.BaseResponse;
import com.microservices.address_service.model.request.AddressRequest;
import com.microservices.address_service.model.response.AddressResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IAddressService {

    ResponseEntity<?> createAddress(Long userId, List<AddressRequest> request);

    List<AddressResponse> getAddressDetails(Long userId);

    ResponseEntity<?> createAddressNew(Long userId, AddressRequest request);
}
