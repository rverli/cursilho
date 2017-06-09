package com.sicpa.thymeleaf.poc.aqualis.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}
