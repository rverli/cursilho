package com.sicpa.thymeleaf.poc.aqualis.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sicpa.thymeleaf.poc.aqualis.exception.ServiceException;
import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.Cursilhista;

/**
 * Interface with methods for operations associated with {@link Cursilhista}
 */
public interface CursilhistaService extends BasicCrudService<Cursilhista, Long> {

	/**
	 * 
	 * List companies based on parameters passed
	 * 
	 * @param active active or not
	 * @param pageable
	 * @return {@link Page} with {@link Cursilhista} objects
	 * @throws ServiceException
	 */
	Page<Cursilhista> findByNumberCursilhistaAndNameAndRetreatNumberAndActiveContaining(
			String numberCursilhista, String name, Long retreatNumber, Boolean active, Pageable pageable) throws ServiceException;
	
	/**
	 * 
	 * List all companies
	 * 
	 * @return List of active {@link Cursilhista}
	 */
	List<Cursilhista> findAllCursilhistas() throws ServiceException;

	/**
	 * 
	 * List all active companies
	 * 
	 * @param active company active or not
	 * @return List of active {@link Cursilhista}
	 * @throws ServiceException when an error occurs locating the Companies
	 */
	List<Cursilhista> findActiveCursilhistas(boolean active) throws ServiceException;
	
	Long findLastNumberCursilhista() throws ServiceException;

	Page<Cursilhista> findByNameAndRetreatNumberAndActiveContaining(String name, Long retreatNumber, Boolean active, Pageable pageable) throws ServiceException;
}
