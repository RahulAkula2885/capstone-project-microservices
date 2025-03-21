package com.microservices.user_service.service;

import com.microservices.user_service.commons.response.BaseResponse;
import com.microservices.user_service.model.request.LoginRequest;
import com.microservices.user_service.model.request.UserRequest;
import com.microservices.user_service.model.response.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface IUserService {
    BaseResponse createUser(UserRequest request);

    List<UserResponse> getUserDetails();
    ResponseEntity<BaseResponse> userLogin(LoginRequest request);

    List<UserResponse> getAllUserDetailsStaticResponse();
}
