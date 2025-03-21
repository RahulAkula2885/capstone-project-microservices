package com.microservices.address_service.controller;

import com.microservices.address_service.commons.exceptions.CustomException;
import com.microservices.address_service.commons.exceptions.GlobalExceptionHandling;
import com.microservices.address_service.commons.response.BaseResponse;
import com.microservices.address_service.model.request.AddressRequest;
import com.microservices.address_service.model.response.AddressResponse;
import com.microservices.address_service.service.IAddressService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequestMapping(path = "/v1/address")
@RequiredArgsConstructor
@Tag(name = "Address Service APIs")
public class AddressController {

    private final GlobalExceptionHandling globalExceptionHandling;
    private final IAddressService iAddressService;


    @ExceptionHandler(CustomException.class)
    public ResponseEntity<BaseResponse> createCustomException(CustomException ex) {
        return globalExceptionHandling.createBaseResponse(INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @Operation(
            description = "Create Address Address",
            summary = "Create Address Address",
            responses = {
                    @ApiResponse(
                            description = "SUCCESS",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "INTERNAL SERVER ERROR",
                            responseCode = "500"
                    )
            }
    )
    @PostMapping(value = "/create/old/{userId}")
    public ResponseEntity<?> createAddress(@PathVariable("userId") Long userId,@RequestBody List<AddressRequest> request) throws IOException {
        return iAddressService.createAddress(userId,request);
    }

    @Operation(
            description = "Create Address Address",
            summary = "Create Address Address",
            responses = {
                    @ApiResponse(
                            description = "SUCCESS",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "INTERNAL SERVER ERROR",
                            responseCode = "500"
                    )
            }
    )
    @PostMapping(value = "/create/{userId}")
    public ResponseEntity<?> createAddressNew(@PathVariable("userId") Long userId,@RequestBody AddressRequest request) throws IOException {
        return iAddressService.createAddressNew(userId,request);
    }

    @Operation(
            description = "Get Address Details By UserId",
            summary = "Get Address Details By UserId",
            responses = {
                    @ApiResponse(
                            description = "SUCCESS",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "INTERNAL SERVER ERROR",
                            responseCode = "500"
                    )
            }
    )
    @GetMapping("/details/{userId}")
    public List<AddressResponse> getAddressDetailsByUserId(@PathVariable("userId") Long userId) throws IOException {
        return iAddressService.getAddressDetails(userId);
    }

}
