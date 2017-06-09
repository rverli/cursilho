package com.sicpa.thymeleaf.poc.aqualis.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

	
}
