package com.sicpa.thymeleaf.poc.aqualis.service;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sicpa.thymeleaf.poc.aqualis.enumerator.AuditingOperationType;
import com.sicpa.thymeleaf.poc.aqualis.exception.ServiceException;
import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.Audit;

/**
 * 
 * Class responsible for operations with {@link Audit}
 *
 */
public interface AuditingService {

	/**
	 * Responsible for saving an {@link Audit}
	 * 
	 * @param audit The {@link Audit} to be saved
	 */
	void save(Audit audit);

	/**
	 * Query used in audit process
	 * @param pagetitle
	 * @param operation
	 * @param userinfo
	 * @param pageaction
	 * @param hostname
	 * @param initialDate
	 * @param finalDate
	 * @param pageable
	 * @return Page<Audit>
	 * @throws ServiceException
	 */
	Page<Audit> findByPageAndOperationAndUserAndHostnameAndDateBetweenInitialDateAndFinalDate(
			String pagetitle, String pageaction, AuditingOperationType operation, String userinfo,
			String hostname, Date initialDate, Date finalDate, Pageable pageable) throws ServiceException;
	
}
