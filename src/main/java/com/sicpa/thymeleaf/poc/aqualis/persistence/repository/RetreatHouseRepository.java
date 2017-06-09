package com.sicpa.thymeleaf.poc.aqualis.persistence.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.RetreatHouse;

/**
 * <p>
 * Interface for operations and queries on {@link RetreatHouse} Entity</br> 
 * Spring generates a proxy implementation so no implementation is required.
 * </p>
 */
public interface RetreatHouseRepository extends JpaRepository<RetreatHouse, Long> {

	/**
	 * <p>
	 * Query for searching active {@link RetreatHouse} by parameters passed in method
	 * </p>	 
	 * @param active RetreatHouse active or not
	 * @param pageable 
	 * @return {@link Page}
	 */
	@Query(   " FROM RetreatHouse u "
			+ " WHERE u.name like CONCAT('%',:name,'%') "
			+ " 	AND u.responsable like CONCAT('%',:responsable,'%') "			
			+ " 	AND (:active is null or u.active = :active) "
			+ " 	AND (u.deleted is null or u.deleted = false) "
			+ " ORDER BY u.name ")
	Page<RetreatHouse> findByNameAndResponsableAndActiveContaining(
			@Param("name")String name, 
			@Param("responsable")String responsable,
			@Param("active")Boolean active,
			Pageable pageable);
	
	/**
	 * <p>
	 * Queries {@link RetreatHouse}
	 * </p>
	 * @return a list of {@link RetreatHouse} populated with id and name
	 */
	@Query("select new RetreatHouse(c.id, c.name) FROM RetreatHouse c order by c.name asc")
	List<RetreatHouse> findRetreatHouseWithIdAndName();

	/**
	 * 
	 * List all active companies ordered by name
	 * @param active company active or not
	 * @return List of active {@link RetreatHouse}
	 */
	List<RetreatHouse> findByActiveOrderByName(boolean active);
	
}
