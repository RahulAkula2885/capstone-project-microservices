package com.microservices.user_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotBlank(message = "Full name is mandatory")
    @Column(name = "fullName")
    private String fullName;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Please enter valid email")
    @Column(name = "email")
    private String email;

    @NotBlank(message = "Mobile number is mandatory")
    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be 10 digits")
    @Column(name = "mobileNumber")
    private String mobileNumber;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Column(name = "password")
    private String password;

    @Column(name = "deleted")
    private boolean deleted;

    @Column(name = "createdTime")
    private Instant createdTime;

    @Column(name = "modifiedTime")
    private Instant modifiedTime;

}
