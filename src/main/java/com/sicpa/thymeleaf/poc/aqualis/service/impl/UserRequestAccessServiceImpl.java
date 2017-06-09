package com.sicpa.thymeleaf.poc.aqualis.service.impl;

import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sicpa.thymeleaf.poc.aqualis.environment.config.AppMessage;
import com.sicpa.thymeleaf.poc.aqualis.exception.ServiceException;
import com.sicpa.thymeleaf.poc.aqualis.messages.SystemMessages;
import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.User;
import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.UserRequestAccess;
import com.sicpa.thymeleaf.poc.aqualis.persistence.repository.UserRequestAccessRepository;
import com.sicpa.thymeleaf.poc.aqualis.service.UserRequestAccessService;
import com.sicpa.thymeleaf.poc.aqualis.service.UserService;
import com.sicpa.thymeleaf.poc.aqualis.utils.AqualisDateUtil;
import com.sicpa.thymeleaf.poc.aqualis.utils.EmailUtil;
import com.sicpa.thymeleaf.poc.aqualis.utils.Mail;

/**
 * Implementation of {@link UserRequestAccessService} 
 * 
 */
@Service
public class UserRequestAccessServiceImpl implements UserRequestAccessService {

	private static final String APP_SERVICE_ERROR_REQUEST_ACCESS_CODE_NOT_FOUND = "app.service.error.requestaccess.requestcode.notfound";
	private static final String APP_SERVICE_ERROR_REQUEST_ACCESS_USER_NOT_FOUND = "app.service.error.requestaccess.user.notfound";
	private static final String APP_SERVICE_ERROR_REQUEST_ACCESS_CODE_EXPIRED = "app.service.error.requestaccess.requestcode.expired";
	private static final String APP_SERVICE_ERROR_REQUEST_ACCESS = "app.service.error.requestaccess";
	
	@Autowired
	private UserRequestAccessRepository userRequestAccessRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private EmailUtil emailUtil;
	
	@Autowired
	private AppMessage appMessage;
	
	@Value("${app.mail.notification.config.mail.from}")
	private String from;
	
	@Value("${app.user.request.access.valid}")
	private long timeValid;
	
	@Override
	public void forgotPasssword(String email) throws ServiceException {

		Validate.notBlank(email, SystemMessages.INVALID_PARAMETER);
		
		User user = userService.findUserByLogin(email);
		
		if( user == null ){
			throw new IllegalArgumentException(SystemMessages.USER_SERVICE_USER_NOTFOUND_KEY);
		}
		
		Date requestValid = AqualisDateUtil.add(new Date(), timeValid);
		UserRequestAccess userRequestAccess = new UserRequestAccess(user, UUID.randomUUID().toString(), requestValid );
		try {
			userRequestAccessRepository.saveAndFlush(userRequestAccess);	
		} catch (Exception e) {
			throw new ServiceException(SystemMessages.USER_REQUEST_ACCESS_SAVE_ERROR, e);
		}
		
		
		Mail mail = new Mail();
		mail.setFrom(from);
		mail.setTo(user.getEmail());
		mail.setSubject(appMessage.getProperty("app.mail.requestpassword.subject"));
		mail.setText(String.format(appMessage.getProperty("app.mail.requestpassword.text"), userRequestAccess.getRequestCode() ));
		
		emailUtil.send(mail);
		
	}

	@Override
	public User getUserByRequestCode(String requestCode) throws ServiceException {
		
		UserRequestAccess userRequestAccess;
		Validate.notBlank(requestCode,SystemMessages.INVALID_PARAMETER);

		try {
			userRequestAccess = userRequestAccessRepository.findByRequestCode(requestCode);
		} catch (Exception e) {
			throw new ServiceException(appMessage.getProperty(APP_SERVICE_ERROR_REQUEST_ACCESS), e);
		}
		
		if (userRequestAccess == null) {
			throw new ServiceException(appMessage.getProperty(APP_SERVICE_ERROR_REQUEST_ACCESS_CODE_NOT_FOUND));
		}
		
		if (userRequestAccess.getUser() == null) {
			throw new ServiceException(appMessage.getProperty(APP_SERVICE_ERROR_REQUEST_ACCESS_USER_NOT_FOUND));
		}
		
		if (hasPasswordRequestExpired(userRequestAccess)) {
			throw new ServiceException(appMessage.getProperty(APP_SERVICE_ERROR_REQUEST_ACCESS_CODE_EXPIRED));
		}
			
		return userRequestAccess.getUser();
	}

	private boolean hasPasswordRequestExpired(UserRequestAccess userRequestAccess) {
		return userRequestAccess.getRequestValid().before(new Date());
	}
}
