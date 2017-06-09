package com.sicpa.thymeleaf.poc.aqualis.service.impl;

import java.util.Collection;
import java.util.List;

import org.h2.util.StringUtils;
import org.hsqldb.lib.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sicpa.thymeleaf.poc.aqualis.exception.ServiceException;
import com.sicpa.thymeleaf.poc.aqualis.messages.SystemMessages;
import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.Profile;
import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.User;
import com.sicpa.thymeleaf.poc.aqualis.persistence.repository.UserRepository;
import com.sicpa.thymeleaf.poc.aqualis.service.AbstractCrudService;
import com.sicpa.thymeleaf.poc.aqualis.service.UserRequestAccessService;
import com.sicpa.thymeleaf.poc.aqualis.service.UserService;

/**
 * Provide services to users
 * @author lrosa1
 */
@Service
@Transactional
public class UserServiceImpl extends AbstractCrudService<User, Long> implements UserService {

	private UserRepository userRepository;

	@Autowired
	private UserRequestAccessService userRequestAccessService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		super(userRepository);
		this.userRepository = userRepository;
	}
		
	private boolean isNewUser(User user) {
		return user.getId() == null;
	}
	
	private boolean emailExists(User user) {
		User userEmail = userRepository.findUserByEmail(user.getEmail());
		return userEmail != null && !user.equals(userEmail);
	}
	
	private void encodePassword(User user) {
		if (user != null && !StringUtil.isEmpty(user.getPassword())) {
			String encryptedPassword = passwordEncoder.encode(user.getPassword());
			user.setPassword(encryptedPassword);
		}
	}
	
	@Override
	protected boolean entityExists(User user) {
		if( user.getId() == null ){
			throw new IllegalArgumentException(SystemMessages.ENTITY_PARAMETER_IS_NULL);
		}
		return userRepository.findOne(user.getId()) != null;
	}
	
	@Override
	protected void validateEntity(User user) throws ServiceException {
		if (isNewUser(user)) {
			encodePassword(user); // encrypts password before save user			
			validateUniqueEntityData(user);
		}else{
			User savedUser = userRepository.findOne(user.getId());
			user.setPassword(savedUser.getPassword());
			//feito para evitar que roles sejam exlcuidos na hora de realizar update
//			if( user.getProfiles() != null ){
//			user.getProfiles().clear();
//			user.getProfiles().addAll(savedUser.getProfiles());
//			}
		}
		
	}
	
	/**
	 * <p>
	 * Activates or deactivates a user
	 * </p>
	 */
	@Override
	protected void activateEntity(User user) {
		user.setActive(!user.isActive());
	}

	/**
	 * <p>
	 * Marks a user for deletion
	 * </p>
	 */
	@Override
	protected void markEntityForDeletion(User user) {
		user.setDeleted(true);
	}

	/**
	 * <p>
	 * Validate information that is unique for every user like CPF and email
	 * </p>
	 * @param user
	 * @throws ServiceException
	 */
	@Override
	protected void validateUniqueEntityData(User user) throws ServiceException {
		if (emailExists(user)) {
			throw new ServiceException(SystemMessages.USER_SERVICE_MAIL_UNIQUE_KEY);
		}
	}
	
	@Override
	public List<User> findAllUsers() throws ServiceException {

		List<User> userList;
		try {
			userList = userRepository.findAll();
		} catch (Exception e) {
			throw new ServiceException(SystemMessages.USER_SERVICE_ERROR_FIND_USER_WITH_PARAMETERS_KEY, e);
		}
		return userList;
	}

	@Override
	public Page<User> findAllUsers(Pageable pageable) throws ServiceException {

		Page<User> userList;
		try {
			userList = userRepository.findAll(pageable);
		} catch (Exception e) {
			throw new ServiceException(SystemMessages.USER_SERVICE_ERROR_FIND_USER_WITH_PARAMETERS_KEY, e);
		}
		return userList;
	}

	@Override
	public Page<User> findUserByEmailContaining(String email, Pageable pageable) throws ServiceException {

		if (StringUtils.isNullOrEmpty(email) || pageable == null) {
			throw new IllegalArgumentException("Parameter 'email' or 'pageable' is Null");
		}

		Page<User> userList;
		try {
			userList = userRepository.findUserByEmailContaining(email, pageable);
		} catch (Exception e) {
			throw new ServiceException(SystemMessages.USER_SERVICE_ERROR_FIND_USER_WITH_PARAMETERS_KEY, e);
		}
		return userList;
	}

	@Override
	public Page<User> findByNameAndEmailAndActiveContaining(String name, String email, Boolean active,
			Pageable pageable) throws ServiceException {
		Page<User> userList;
		try {
			userList = userRepository.findByNameAndEmailAndActiveContaining(name, email, active, pageable);
		} catch (Exception e) {
			throw new ServiceException(SystemMessages.USER_SERVICE_ERROR_FIND_USER_WITH_PARAMETERS_KEY, e);
		}
		return userList;
	}

	@Override
	public User findUserByLogin(String login) throws ServiceException {
		User user = null;
		try {
			user = userRepository.findUserByEmail(login);
		} catch (Exception e) {
			throw new ServiceException(SystemMessages.USER_SERVICE_ERROR_FIND_USER_WITH_PARAMETERS_KEY, e);
		}
		return user;
	}
	
	@Override
	public User findUserById(Long id) throws ServiceException {
		User user = null;
		try {
			user = userRepository.findUserById( id );
		} catch (Exception e) {
			throw new ServiceException(SystemMessages.USER_SERVICE_ERROR_FIND_USER_WITH_PARAMETERS_KEY, e);
		}
		return user;
	}
	
	@Override
	public void updatePassword(User user, String oldPassword, String newPassword) throws ServiceException {
		
		if (user == null || !passwordEncoder.matches(oldPassword, user.getPassword())) {
			throw new ServiceException(SystemMessages.USER_SERVICE_WRONG_USER_PASS);
		}

		updatePassword(user, newPassword);
	}
	
	@Override
	public void updatePasswordByRequestCode(String requestCode, String newPassword) throws ServiceException {

		if(StringUtils.isNullOrEmpty(requestCode) || StringUtils.isNullOrEmpty(newPassword)){
			throw new IllegalArgumentException(SystemMessages.INVALID_PARAMETER);
		}
		
		User user = userRequestAccessService.getUserByRequestCode(requestCode);
		updatePassword(user, newPassword);
	}
	
	private void updatePassword(User user, String password) throws ServiceException {
		if (user == null || StringUtils.isNullOrEmpty(password)) {
			throw new ServiceException(SystemMessages.USER_SERVICE_ERROR_UPDATE_PASS_KEY);
		}
		
		user.setPassword(password);
		encodePassword(user);
		try {
			userRepository.saveAndFlush(user);
		} catch (Exception e) {
			throw new ServiceException(SystemMessages.USER_SERVICE_ERROR_UPDATE_PASS_KEY, e);
		}		
	}

	@Override
	public List<User> findAllUsersInProfiles(Collection<Profile> profiles) throws ServiceException {
		
		if (profiles == null || profiles.isEmpty()) {
			throw new IllegalArgumentException("Parameter 'profiles' is null or empty.");
		}
		
		List<User> users;
		
		try {
			users = userRepository.findByProfilesIn(profiles);
		} catch (Exception ex) {
			throw new ServiceException(SystemMessages.USER_SERVICE_ERROR_FIND_USER_WITH_PARAMETERS_KEY, ex);
		}
		return users;
	}

}
