package com.microservices.address_service.repository;

import com.microservices.address_service.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IAddressRepository extends JpaRepository<Address,Long> {

    @Query("SELECT o from Address o where o.userId =?1 order by o.createdTime desc")
    List<Address> findAllByUserId(Long userId);
}
