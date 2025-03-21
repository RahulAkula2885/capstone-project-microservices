package com.microservices.user_service.service;

import com.microservices.user_service.commons.exceptions.CustomException;
import com.microservices.user_service.commons.response.BaseResponse;
import com.microservices.user_service.entity.User;
import com.microservices.user_service.model.request.AddressRequest;
import com.microservices.user_service.model.request.LoginRequest;
import com.microservices.user_service.model.request.UserRequest;
import com.microservices.user_service.model.response.AddressResponse;
import com.microservices.user_service.model.response.UserResponse;
import com.microservices.user_service.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.microservices.user_service.commons.util.DateTimeUtil.getInstant;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements IUserService {

    private final IUserRepository iUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final RestTemplate restTemplate;
    private final AddressFeignClient addressFeignClient;

    @Transactional
    @Override
    public BaseResponse createUser(UserRequest request) {
        if (!StringUtils.hasText(request.getFullName())) {
            throw new CustomException("Full name must not be null or empty");
        }
        if (!StringUtils.hasText(request.getEmail())) {
            throw new CustomException("Email must not be null or empty");
        }
        if (!StringUtils.hasText(request.getPassword())) {
            throw new CustomException("Password must not be null or empty");
        }
        if (!StringUtils.hasText(request.getMobileNumber())) {
            throw new CustomException("Mobile number must not be null or empty");
        }

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setMobileNumber(request.getMobileNumber());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setDeleted(false);
        user.setCreatedTime(getInstant());
        user.setModifiedTime(getInstant());
        User details = iUserRepository.save(user);

        log.info("Created user details:- " + details);

        /*String url = "http://localhost:6000/v1/address/create/" + details.getId();
        System.out.println(url);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json"); // Set headers if required

        HttpEntity<List<AddressRequest>> requestEntity = new HttpEntity<>(request.getAddress(), headers);

        ResponseEntity<BaseResponse> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                BaseResponse.class
        );*/

      /*  String url = "http://localhost:6000/v1/address/create/" + details.getId(); // Make sure Address Service is running

        ResponseEntity<AddressRequest> responseEntity = restTemplate.postForEntity(url,request.getAddress(), AddressRequest.class);
      */
        String url = "http://localhost:6000/v1/address/create/old/" + details.getId(); // Make sure Address Service is running

        ResponseEntity<AddressRequest> responseEntity = restTemplate.postForEntity(url, request.getAddress(), AddressRequest.class);

        log.info("Created Address Based on User:- " + responseEntity.getBody());
        return BaseResponse.builder()
                .status(true)
                .message("SUCCESS")
                .systemTime(getInstant())
                .build();
    }

    @Override
    public List<UserResponse> getUserDetails() {

        log.info("initiated user details request");

        List<UserResponse> userResponses = new ArrayList<>();

        List<User> users = iUserRepository.findAll();

        if (!users.isEmpty()) {
            users.forEach(a -> {
                UserResponse userResponse = UserResponse.builder().build();
                BeanUtils.copyProperties(a, userResponse);

                /*String url = "http://localhost:8765/ADDRESS-SERVICE/v1/address/details/" + a.getId();
                //"http://localhost:6000/v1/address/details/" + a.getId();
                List<AddressResponse> addressResponses = restTemplate.getForObject(url, List.class);
                */

                List<AddressResponse> addressResponses = addressFeignClient.getAddressDetailsByUserId(a.getId());


                userResponse.setAddress(addressResponses);
                userResponses.add(userResponse);
            });
        }

        log.info("Successfully fetched user details");

        return userResponses;
    }

    @Override
    public ResponseEntity<BaseResponse> userLogin(LoginRequest request) {

       /* if (bindingResult.hasErrors()) {
            // Collect validation errors
            StringBuilder errorMessage = new StringBuilder("Validation failed: ");
            bindingResult.getAllErrors().forEach(error -> {
                errorMessage.append(error.getDefaultMessage()).append("; ");
            });
            // Throw a CustomException or return the error directly in response
            throw new CustomException(errorMessage.toString());
        }
*/
        Optional<User> user = iUserRepository.findByEmail(request.getEmail());
        if (!user.isPresent()) {
            log.error("Email you have entered is not registered");
            throw new CustomException("Email you have entered is not registered");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.get().getPassword())) {
            log.error("Username / password incorrect. Please try again");
            throw new CustomException("Username / password incorrect. Please try again");
        }

        BaseResponse baseResponse = BaseResponse.builder()
                .status(true)
                .message("SUCCESS")
                .systemTime(getInstant())
                .build();
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @Override
    public List<UserResponse> getAllUserDetailsStaticResponse() {
        return Stream.of(
                UserResponse.builder()
                        .id(1L)
                        .fullName("John Doe Static Data")
                        .email("john.doe@example.com")
                        .mobileNumber("123-456-7890")
                        .deleted(false)
                        .createdTime(Instant.now())  // 1 hour ago
                        .modifiedTime(Instant.now()) // 30 minutes ago
                        .address(new ArrayList<>())
                        .build(),
                UserResponse.builder()
                        .id(2L)
                        .fullName("Jane Smith Static Data")
                        .email("jane.smith@example.com")
                        .mobileNumber("098-765-4321")
                        .deleted(false)
                        .createdTime(Instant.now().minusSeconds(7200))  // 2 hours ago
                        .modifiedTime(Instant.now().minusSeconds(3600)) // 1 hour ago
                        .address(new ArrayList<>())
                        .build()
        ).collect(Collectors.toList());

    }
}
