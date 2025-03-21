package com.microservices.user_service.model.response;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@Builder
public class UserResponse {

    private Long id;
    private String fullName;
    private String email;
    private String mobileNumber;
    private boolean deleted;
    private Instant createdTime;
    private Instant modifiedTime;
    private List<AddressResponse> address;
}
