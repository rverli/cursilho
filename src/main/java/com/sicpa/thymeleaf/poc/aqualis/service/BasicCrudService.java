package com.sicpa.thymeleaf.poc.aqualis.service;

import java.io.Serializable;

import com.sicpa.thymeleaf.poc.aqualis.exception.ServiceException;

/**
 * 
 * Interface with crud operations
 * 
 * @author ekoetsier
 *
 * @param <T>
 * @param <I>
 */
public interface BasicCrudService<T, I extends Serializable> {

	/**
	 * Method responsible for seeking the entity by id.
	 * @param id
	 * @return Company
	 * @throws ServiceException
	 */
	T findOne(I id) throws ServiceException;
	
	/**
	 * Method responsible for saving a entity.
	 * @param company
	 * @throws ServiceException
	 */
	void save(T entity) throws ServiceException;
	
	/**
	 * Method responsible for ativate/deativate the entity by id
	 * @param id
	 * @throws ServiceException
	 */
	void activate(I id) throws ServiceException;

	/**
	 * Method responsible for deleting the entity by id
	 * @param id
	 * @throws ServiceException
	 */
	void delete(I id) throws ServiceException;
	
	/**
	 * Method responsible for changing the entity
	 * @param id
	 * @throws ServiceException
	 */
	void edit(T entity) throws ServiceException;
}
