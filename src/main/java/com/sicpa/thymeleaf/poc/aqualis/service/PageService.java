package com.sicpa.thymeleaf.poc.aqualis.service;

import java.util.List;

import com.sicpa.thymeleaf.poc.aqualis.exception.ServiceException;
import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.Page;

/**
 * 
 * Interface for operations on a {@link Page}
 * 
 *
 */
public interface PageService {
	
	/**
	 * Search a {@link Page} by its path
	 * 
	 * @param path
	 * @return
	 * @throws ServiceException
	 */
	Page findPageByPath(String path) throws ServiceException;
	
	/**
	 * List all pages
	 * 
	 * @return
	 */
	List<Page> findAll();

	/**
	 * 
	 * List actions for a page
	 * 
	 * @param title a page title
	 * @return List with action names
	 * @throws ServiceException
	 */
	List<String> findAllActionsByTitle(String title) throws ServiceException;
	
	/**
	 * 
	 * List all page titles
	 * 
	 * @return List with page titles
	 * @throws ServiceException
	 */
	List<String> findAllTitles() throws ServiceException;
}
