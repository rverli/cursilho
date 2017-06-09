package com.sicpa.thymeleaf.poc.aqualis.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.UserRequestAccess;

/**
 * Repository for operations and queries on a {@link UserRequestAccess}
 * @author mvale
 */
@Repository
public interface UserRequestAccessRepository extends JpaRepository<UserRequestAccess, Long> {

	/**
	 * @param requestCode
	 * @return
	 */
	UserRequestAccess findByRequestCode(String requestCode);
}
