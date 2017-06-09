package com.sicpa.thymeleaf.poc.aqualis.persistence.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sicpa.thymeleaf.poc.aqualis.enumerator.AuditingOperationType;
import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.Audit;

/**
 * Allows access to auditing repository.
 * @author Gsalomao
 */
public interface AuditingRepository extends JpaRepository<Audit, Long> {


	@Query(value = 
			  "SELECT a "
			+ "FROM Audit a "
			+ "INNER JOIN a.user u "
			+ "INNER JOIN a.page p "
			+ "WHERE (:userinfo is null or upper(u.name) like upper(CONCAT('%', :userinfo, '%')) "
			+ "  		or upper(u.email) like upper(CONCAT('%', :userinfo, '%'))) "
			+ "  and (:hostName is null or a.hostName = :hostName) "
			+ "  and (:pagetitle is null or p.title = :pagetitle) "
			+ "  and (:pageaction is null or p.action = :pageaction) "
			+ "  and (:auditingOperationType is null or a.auditingOperationType = :auditingOperationType) "
			+ "  and (:beginDateTime is null or a.dateTime >= :beginDateTime) "
			+ "  and (:endDateTime is null or a.dateTime <= :endDateTime) "
			)
	Page<Audit> findByPageAndAuditingOperationTypeAndUserAndHostNameAndDateTimeBetween(
			@Param("userinfo") String userinfo, 
			@Param("hostName") String hostName, 
			@Param("pagetitle") String pagetitle, 
			@Param("pageaction") String pageaction, 
			@Param("auditingOperationType") AuditingOperationType auditingOperationType, 
			@Param("beginDateTime") Date beginDateTime,			
			@Param("endDateTime") Date endDateTime,
			Pageable pageable);	

}
