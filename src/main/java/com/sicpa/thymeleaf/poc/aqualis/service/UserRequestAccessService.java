package com.sicpa.thymeleaf.poc.aqualis.service;

import com.sicpa.thymeleaf.poc.aqualis.exception.ServiceException;
import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.User;

/**
 * Methods for requesting and creating a new password
 *
 */
public interface UserRequestAccessService {

	/**
	 * Validates if the user exists if not throws a {@link ServiceException}
	 * and if the user exists sends the user a link to create a new password
	 * 
	 * @param email
	 */
	void forgotPasssword(String email) throws ServiceException;

	/**
	 * Return the user by code if not expired 
	 * 
	 * @param requestCode
	 * @return
	 * @throws ServiceException
	 */
	User getUserByRequestCode(String requestCode) throws ServiceException;

}
