package com.microservices.user_service.repository;

import com.microservices.user_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User,Long> {

    @Query("SELECT o from User o where o.email =?1")
    Optional<User> findByEmail(String email);
}
