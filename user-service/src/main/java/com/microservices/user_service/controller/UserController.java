package com.microservices.user_service.controller;

import com.microservices.user_service.commons.exceptions.CustomException;
import com.microservices.user_service.commons.exceptions.GlobalExceptionHandling;
import com.microservices.user_service.commons.response.BaseResponse;
import com.microservices.user_service.entity.User;
import com.microservices.user_service.model.request.LoginRequest;
import com.microservices.user_service.model.request.UserRequest;
import com.microservices.user_service.model.response.AddressResponse;
import com.microservices.user_service.model.response.UserResponse;
import com.microservices.user_service.repository.IUserRepository;
import com.microservices.user_service.service.IUserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequestMapping(path = "/v1/user")
@RequiredArgsConstructor
@Tag(name = "User Service APIs")
public class UserController {

    private final GlobalExceptionHandling globalExceptionHandling;
    private final IUserService iUserService;

    @Lazy
    private final RestTemplate restTemplate;
    private final IUserRepository iUserRepository;

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<BaseResponse> createCustomException(CustomException ex) {
        return globalExceptionHandling.createBaseResponse(INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @Operation(
            description = "Create User",
            summary = "Create User",
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
    @PostMapping(value = "/create")
    public BaseResponse createUser(@RequestBody UserRequest request) throws IOException {
        return iUserService.createUser(request);
    }

    @Operation(
            description = "Get User Details",
            summary = "Get User Details",
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
    @GetMapping("/details")
    @CircuitBreaker(name = "userService", fallbackMethod = "getAllUserDetailsStaticResponse")
    public List<UserResponse> getUserDetails() throws IOException {
        return iUserService.getUserDetails();
        /*String url = "http://localhost:6001/v1/address/details/" + 1;
        List<AddressResponse> addressResponses = restTemplate.getForObject(url, List.class);

        List<UserResponse> userResponses = new ArrayList<>();

        List<User> users = iUserRepository.findAll();

        if (!users.isEmpty()) {
            users.forEach(a -> {
                UserResponse userResponse = UserResponse.builder().build();
                BeanUtils.copyProperties(a, userResponse);
                //String url = "http://localhost:8765/ADDRESS-SERVICE/v1/address/details/" + a.getId();
                //"http://localhost:6000/v1/address/details/" + a.getId();

               // List<AddressResponse> addressResponses = restTemplate.getForObject(url, List.class);

                userResponse.setAddress(addressResponses);
                userResponses.add(userResponse);
            });
        }

        return userResponses;*/
    }

    public List<UserResponse> getAllUserDetailsStaticResponse(Exception e) {
        return iUserService.getAllUserDetailsStaticResponse();
    }

    @Operation(
            description = "User Login",
            summary = "User Login",
            responses = {
                    @ApiResponse(
                            description = "SUCCESS",
                            responseCode = "200"
                    )
            }
    )
    @PostMapping(path = "/login")
    public ResponseEntity<BaseResponse> userLogin(@Validated @RequestBody LoginRequest request) throws Throwable {
        return iUserService.userLogin(request);
    }

}
