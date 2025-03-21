package com.microservices.user_service.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Please enter valid email")
    public String email;

    @NotBlank(message = "Password is mandatory")
    public String password;
}
