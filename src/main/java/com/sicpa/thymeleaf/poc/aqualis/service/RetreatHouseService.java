package com.sicpa.thymeleaf.poc.aqualis.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sicpa.thymeleaf.poc.aqualis.exception.ServiceException;
import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.RetreatHouse;

/**
 * Interface with methods for operations associated with {@link RetreatHouse}
 */
public interface RetreatHouseService extends BasicCrudService<RetreatHouse, Long> {

	/**
	 * 
	 * List companies based on parameters passed
	 * 
	 * @param name
	 * @param responsable
	 * @param active active or not
	 * @param pageable
	 * @return {@link Page} with {@link RetreatHouse} objects
	 * @throws ServiceException
	 */
	Page<RetreatHouse> findByNameAndResponsableAndActiveContaining( String name, String responsable, Boolean active, Pageable pageable ) throws ServiceException;
	
	/**
	 * 
	 * List all companies
	 * 
	 * @return List of active {@link RetreatHouse}
	 */
	List<RetreatHouse> findAllRetreatHouses() throws ServiceException;

	/**
	 * 
	 * List all active companies
	 * 
	 * @param active company active or not
	 * @return List of active {@link RetreatHouse}
	 * @throws ServiceException when an error occurs locating the Companies
	 */
	List<RetreatHouse> findActiveRetreatHouses(boolean active) throws ServiceException;

}
