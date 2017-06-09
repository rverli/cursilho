package com.sicpa.thymeleaf.poc.aqualis.service;

import java.util.List;

import com.sicpa.thymeleaf.poc.aqualis.exception.ServiceException;
import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.Profile;

/**
 * Profile Service
 * @author lrosa1
 *
 */
public interface ProfileService extends BasicCrudService<Profile, Long> {
	
	/**
	 * Locate all profiles
	 * @return
	 * @throws ServiceException
	 */
	List<Profile> findAll() throws ServiceException;
	
}