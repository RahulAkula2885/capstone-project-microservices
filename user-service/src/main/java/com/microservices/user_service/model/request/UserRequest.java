package com.microservices.user_service.model.request;

import lombok.Data;

import java.util.List;

@Data
public class UserRequest {

    public String fullName;
    public String email;
    public String password;
    public String mobileNumber;
    public List<AddressRequest> address;
    //public AddressRequest address;
}
