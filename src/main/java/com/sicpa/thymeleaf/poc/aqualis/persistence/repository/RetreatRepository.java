package com.sicpa.thymeleaf.poc.aqualis.persistence.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.Retreat;

/**
 * <p>
 * Interface for operations and queries on {@link Retreat} Entity</br> 
 * Spring generates a proxy implementation so no implementation is required.
 * </p>
 */
public interface RetreatRepository extends JpaRepository<Retreat, Long> {

	/**
	 * <p>
	 * Query for searching active {@link Retreat} by parameters passed in method
	 * </p>	 
	 * @param active Retreat active or not
	 * @param pageable 
	 * @return {@link Page}
	 */
	@Query(value = 
			  " SELECT u "
			+ " FROM Retreat as u "
			+ " 	INNER JOIN u.retreatHouse as r "
			+ " WHERE (:number is null or u.number = :number) "
			+ " 	AND (u.coordinator LIKE CONCAT('%',:coordinator,'%')) "
			+ " 	AND (r.name LIKE CONCAT('%',:retreatHouse,'%')) "
			+ " 	AND (:active is null or u.active = :active) "
			+ " 	AND (u.deleted is null or u.deleted = false) "
			+ " ORDER BY u.number desc ")
	Page<Retreat> findByNumberAndCoordinatorAndRetreatHouseAndActiveContaining(
			@Param("number") Long number, 
			@Param("coordinator") String coordinator,
			@Param("retreatHouse") String retreatHouse,
			@Param("active") Boolean active,
			Pageable pageable);
	
	/**
	 * <p>
	 * Queries {@link Retreat}
	 * </p>
	 * @return a list of {@link Retreat} populated with id and name
	 */
	@Query("FROM Retreat c ORDER BY c.number DESC")
	List<Retreat> findRetreatWithIdAndNumber();

	/**
	 * 
	 * List all active companies ordered by name
	 * @param active company active or not
	 * @return List of active {@link Retreat}
	 */
	List<Retreat> findByActiveOrderByNumber(boolean active);
	
	@Query("from Retreat c order by c.number DESC")
	List<Retreat> findLastNumberRetreat();
	
	@Query("FROM Retreat c WHERE c.id = :id")
	Retreat findRetreatById(@Param("id") Long id);
	
	@Query("FROM Retreat c WHERE c.id = :id")
	Page<Retreat> findRetreatByIdPage(@Param("id") Long id, Pageable pageable);
}
