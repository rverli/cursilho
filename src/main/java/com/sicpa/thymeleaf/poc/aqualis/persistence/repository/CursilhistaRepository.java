package com.sicpa.thymeleaf.poc.aqualis.persistence.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.Cursilhista;

/**
 * <p>
 * Interface for operations and queries on {@link Company} Entity</br> 
 * Spring generates a proxy implementation so no implementation is required.
 * </p>
 */
public interface CursilhistaRepository extends JpaRepository<Cursilhista, Long> {

	/**
	 * <p>
	 * Query for searching active {@link Cursilhista} by parameters passed in method
	 * </p>
	 * @param active company active or not
	 * @param pageable 
	 * @return {@link Page}
	 */
	@Query(value = 
			  " SELECT u "
			+ " FROM Cursilhista u "
			+ " 	INNER JOIN u.retreat as r "
			+ " WHERE (u.numberCursilhista like CONCAT('%',:numberCursilhista,'%')) "
			+ " 	AND (u.name like CONCAT('%',:name,'%')) "			
			+ " 	AND (:retreatNumber is null or r.number = :retreatNumber) "
			+ " 	AND (:active is null or u.active = :active) "
			+ " 	AND (u.deleted is null or u.deleted = false) "
			+ " 	AND (u.numberCursilhista IS NOT NULL AND u.numberCursilhista > 0) "
			+ " ORDER BY u.name ") 
	Page<Cursilhista> findByNumberCursilhistaAndNameAndRetreatNumberAndActiveContaining(
			@Param("numberCursilhista")String numberCursilhista, 
			@Param("name")String name,
			@Param("retreatNumber")Long retreatNumber, 
			@Param("active")Boolean active,
			Pageable pageable);
	
	@Query(value = 
			  " SELECT u "
			+ " FROM Cursilhista u "
			+ " 	INNER JOIN u.retreat as r "
			+ " WHERE (:name IS NULL OR u.name like CONCAT('%',:name,'%')) "			
			+ " 	AND (:retreatNumber is null or r.number = :retreatNumber) "
			+ " 	AND (u.numberCursilhista = 0) "
			+ " 	AND (:active is null or u.active = :active) "
			+ " 	AND (u.deleted is null or u.deleted = false) "
			+ " ORDER BY u.name ") 
	Page<Cursilhista> findCursilhistaWithoutNumberCursilhista(
			@Param("name")String name,
			@Param("retreatNumber")Long retreatNumber, 
			@Param("active")Boolean active,
			Pageable pageable);
	
	
	/**
	 * <p>
	 * Query for searching active {@link Candidate} by parameters passed in method
	 * </p>
	 * @param active Candidate active or not
	 * @param pageable 
	 * @return {@link Page}
	 */
	@Query(value = 
			  " SELECT u "
			+ " FROM Cursilhista u "
			+ " 	INNER JOIN u.retreat as r "
			+ " WHERE (u.name like CONCAT('%',:name,'%')) "			
			+ " 	AND (:retreatNumber is null or r.number = :retreatNumber) "
			+ " 	AND (:active is null or u.active = :active) "
			+ " 	AND (u.deleted is null or u.deleted = false) "
			+ " 	AND (u.numberCursilhista is null or u.numberCursilhista = 0) "
			+ " ORDER BY u.name ") 
	Page<Cursilhista> findByNameAndRetreatNumberAndActiveContaining(
			@Param("name")String name,
			@Param("retreatNumber")Long retreatNumber, 
			@Param("active")Boolean active,
			Pageable pageable);
	
	/**
	 * <p>
	 * Queries {@link Cursilhista}
	 * </p>
	 * @return a list of {@link Cursilhista} populated with id and name
	 */
	@Query("select new Cursilhista(c.id, c.name) FROM Cursilhista c order by c.name asc")
	List<Cursilhista> findCursilhistaWithIdAndName();

	/**
	 * 
	 * List all active companies ordered by name
	 * @param active company active or not
	 * @return List of active {@link Company}
	 */
	List<Cursilhista> findByActiveOrderByName(boolean active);
	
	
	@Query("from Cursilhista c order by c.numberCursilhista DESC")
	List<Cursilhista> findLastNumberCursilhista();
	
	List<Cursilhista> findCursilhistaByRetreatId(Long idRetreat);
	
	@Query(value = 
			  " SELECT u "
			+ " FROM Cursilhista u "
			+ " 	INNER JOIN u.retreat as r "
			+ " WHERE r.id = :idRetreat "
			+ " 	AND u.active = true "
			+ " 	AND u.deleted = false "
			+ " 	AND ( u.numberCursilhista IS NULL OR u.numberCursilhista <= 0) "
			+ " ORDER BY u.name ") 
	List<Cursilhista> findByReport(
			@Param("idRetreat")Long retreatNumber);
}
