package com.microservices.address_service.service;

import com.microservices.address_service.commons.exceptions.CustomException;
import com.microservices.address_service.commons.response.BaseResponse;
import com.microservices.address_service.entity.Address;
import com.microservices.address_service.model.request.AddressRequest;
import com.microservices.address_service.model.response.AddressResponse;
import com.microservices.address_service.repository.IAddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static com.microservices.address_service.commons.util.DateTimeUtil.getInstant;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements IAddressService {

    private final IAddressRepository iAddressRepository;

    @Override
    public ResponseEntity<?> createAddress(Long userId, List<AddressRequest> request) {

        if (!request.isEmpty()) {

            validateRequest(userId, request);

            List<Address> addresses = new ArrayList<>();
            request.forEach(a -> {
                Address address = new Address();
                address.setUserId(userId);
                address.setCity(a.getCity());
                address.setState(a.getState());
                address.setCountry(a.getCountry());
                address.setAddress(a.getAddress());
                address.setPinCode(a.getPinCode());
                address.setDeleted(false);
                address.setCreatedTime(getInstant());
                address.setModifiedTime(getInstant());

                addresses.add(address);
            });

            if (!addresses.isEmpty()) {
                iAddressRepository.saveAll(addresses);
            }

            //return new ResponseEntity<>(null, HttpStatus.OK);
            BaseResponse baseResponse = BaseResponse.builder()
                    .status(true)
                    .message("SUCCESS")
                    .systemTime(getInstant())
                    .build();

            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        } else {
            throw new CustomException("Address must not be null or empty");
        }

    }

    @Override
    public List<AddressResponse> getAddressDetails(Long userId) {
        List<AddressResponse> addressResponses = new ArrayList<>();
        if (userId == null || userId == 0) {
            throw new CustomException("User id must not be null or 0");
        }
        List<Address> addresses = iAddressRepository.findAllByUserId(userId);
        if (!addresses.isEmpty()) {
            addresses.forEach(a -> {
                AddressResponse addressResponse = AddressResponse.builder().build();
                BeanUtils.copyProperties(a, addressResponse);
                addressResponses.add(addressResponse);
            });
        }
        return addressResponses;
    }

    @Override
    public ResponseEntity<?> createAddressNew(Long userId, AddressRequest a) {
        Address address = new Address();
        address.setUserId(userId);
        address.setCity(a.getCity());
        address.setState(a.getState());
        address.setCountry(a.getCountry());
        address.setAddress(a.getAddress());
        address.setPinCode(a.getPinCode());
        address.setDeleted(false);
        address.setCreatedTime(getInstant());
        address.setModifiedTime(getInstant());
        iAddressRepository.save(address);

        return new ResponseEntity<>(null,HttpStatus.OK);
    }

    private void validateRequest(Long userId, List<AddressRequest> request) {
        if (userId == null || userId == 0) {
            throw new CustomException("User id must not be null or 0");
        }
        request.forEach(a -> {
            if (!StringUtils.hasText(a.getCity())) {
                throw new CustomException("City must not be null or empty");
            }
            if (!StringUtils.hasText(a.getState())) {
                throw new CustomException("State must not be null or empty");
            }
            if (!StringUtils.hasText(a.getCountry())) {
                throw new CustomException("Country must not be null or empty");
            }
            if (!StringUtils.hasText(a.getAddress())) {
                throw new CustomException("Address must not be null or empty");
            }
            if (!StringUtils.hasText(a.getPinCode())) {
                throw new CustomException("Pin code must not be null or empty");
            }
        });
    }
}
