package com.sicpa.thymeleaf.poc.aqualis.persistence.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.Profile;
import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.User;

/**
 * <p>
 * Interface responsible for querying users, implementation in generated by Spring
 * </p>
 * @author ekoetsier
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	/**
	 * <p>
	 * Locate a users using part of the e-mail returns a {@link Page} with the users found
	 * </p>
	 * @param email
	 * @param pageable
	 * @return
	 */
	@Query("FROM User u where u.email like CONCAT('%',:email,'%')")
	Page<User> findUserByEmailContaining(@Param("email") String email, Pageable pageable);
	
	/**
	 * <p>
	 * Locate a single user using the exact email informed
	 * </p>
	 * @param email
	 * @return
	 */
	@Query("FROM User u where u.email =:email and u.active=true and u.deleted=false")
	User findUserByEmail(@Param("email") String email);
	
	@Query("FROM User u where u.id =:id and u.active=true and u.deleted=false")
	User findUserById(@Param("id") Long id);
	
	/**
	 * <p>
	 * Locate a users using the parameters informaed. Returns a {@link Page} with the users found
	 * </p>
	 * @param name
	 * @param email
	 * @param cpf
	 * @param active
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	@Query("FROM User u where u.name like CONCAT('%',:name,'%') "
			+ " and u.email like CONCAT('%',:email,'%') "
			+ " and (:active is null or u.active = :active) "
			+ " and (u.deleted is null or u.deleted = false) ") 
	Page<User> findByNameAndEmailAndActiveContaining(@Param("name") String name, 
															@Param("email") String email, 
															@Param("active") Boolean active, 
															Pageable pageable);
	
	/**
	 * <p>
	 * Locates all users by roles
	 * </p>
	 * @param profiles
	 * @return
	 */
	List<User> findByProfilesIn(Collection<Profile> profiles);
}