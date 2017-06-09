package com.sicpa.thymeleaf.poc.aqualis.service;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sicpa.thymeleaf.poc.aqualis.exception.ServiceException;
import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.Profile;
import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.User;

public interface UserService extends BasicCrudService<User, Long> {

	/**
	 * 
	 * @return
	 */
	List<User> findAllUsers() throws ServiceException;

	Page<User> findAllUsers(Pageable pageable) throws ServiceException;

	Page<User> findUserByEmailContaining(String email, Pageable pageable) throws ServiceException;

	Page<User> findByNameAndEmailAndActiveContaining(String name, String email, Boolean active,
			Pageable pageable) throws ServiceException;

	User findUserByLogin(String login) throws ServiceException;
	
	/**
	 * Method that updates the User password if this does not match with the old one
	 * @param user User with the old password
	 * @param oldPassword Old password without encription
	 * @param newPassword new password 
	 * @throws ServiceException
	 */
	void updatePassword(User user, String oldPassword, String newPassword) throws ServiceException;

	/**
	 * Method that updates the User password by request code
	 * @param requestCode request code
	 * @param newPassword new password 
	 * @throws ServiceException
	 */
	void updatePasswordByRequestCode(String requestCode, String newPassword) throws ServiceException;

	/**
	 * Find all users that contains the specified profiles
	 * @param profiles
	 * @return List<User>
	 * @throws ServiceException
	 */
	List<User> findAllUsersInProfiles(Collection<Profile> profiles)  throws ServiceException;

	User findUserById(Long id) throws ServiceException;
}
