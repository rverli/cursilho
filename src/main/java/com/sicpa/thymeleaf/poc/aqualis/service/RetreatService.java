package com.sicpa.thymeleaf.poc.aqualis.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sicpa.thymeleaf.poc.aqualis.exception.ServiceException;
import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.Retreat;

/**
 * Interface with methods for operations associated with {@link Retreat}
 */
public interface RetreatService extends BasicCrudService<Retreat, Long> {

	/**
	 * 
	 * List Retreats based on parameters passed
	 *
	 * @param active active or not
	 * @param pageable
	 * @return {@link Page} with {@link Retreat} objects
	 * @throws ServiceException
	 */
	Page<Retreat> findByNumberAndCoordinatorAndRetreatHouseAndActiveContaining( Long number, String coordenador, String retreatHouse, Boolean active, Pageable pageable ) throws ServiceException;
	
	/**
	 * 
	 * List all companies
	 * 
	 * @return List of active {@link Retreat}
	 */
	List<Retreat> findAllRetreats() throws ServiceException;

	/**
	 * 
	 * List all active companies
	 * 
	 * @param active company active or not
	 * @return List of active {@link Retreat}
	 * @throws ServiceException when an error occurs locating the Companies
	 */
	List<Retreat> findActiveRetreats(boolean active) throws ServiceException;

	Long findLastNumberRetreat() throws ServiceException;
	
	Retreat findRetreatById(Long id) throws ServiceException;
	
	Page<Retreat> findRetreatByIdPage(Long id, Pageable pageable) throws ServiceException;
}
