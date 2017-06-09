package com.sicpa.thymeleaf.poc.aqualis.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sicpa.thymeleaf.poc.aqualis.exception.ControllerRestException;
import com.sicpa.thymeleaf.poc.aqualis.exception.ServiceException;
import com.sicpa.thymeleaf.poc.aqualis.messages.Message;
import com.sicpa.thymeleaf.poc.aqualis.messages.SystemMessages;
import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.User;
import com.sicpa.thymeleaf.poc.aqualis.security.PermissionList;
import com.sicpa.thymeleaf.poc.aqualis.service.UserService;
import com.sicpa.thymeleaf.poc.aqualis.utils.AuthUtil;
import com.sicpa.thymeleaf.poc.aqualis.utils.ProjectMapping;
import com.sicpa.thymeleaf.poc.aqualis.utils.form.validators.fieldmatch.FieldMatchGroup;
import com.sicpa.thymeleaf.poc.aqualis.utils.form.validators.password.PasswordGroup;

/**
 * Controller for authenticating users who want to enter the system
 *
 */
@Controller
public class LoginController {
	
	public static final String REDIRECT_LOGOUT = "redirect:/login?logout";

	private static final String USER_ATTRIBUTE_KEY = "user";

	private static final String USER_LOGIN_URL = "userLogin";

	private Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	static final String TEMPLATE_LOGIN_EDIT = ProjectMapping.LOGIN.BASE_MAPPING + "/" + ProjectMapping.EDIT_PAGE;
	
	@Autowired
	private UserService userService; 
	
	@RequestMapping(value="/login", method = RequestMethod.GET)
	String login() {
		if (AuthUtil.getAuthenticatedUser().getPrincipal() instanceof String){
			return USER_LOGIN_URL;
		}else{
			return "home";
		}
	}

	
	/**
	 * <p>
	 * Shows page with a user to edited with the id used
	 * </p>
	 * @param id
	 * @param model
	 * @return
	 * @throws ControllerRestException
	 */
	@Secured(PermissionList.LOGIN.EDIT)
	@RequestMapping(value = "/login/edit", method = RequestMethod.GET)
	String editLogin(Model model) throws ControllerRestException {
		
		try {
			User user = (User) AuthUtil.getAuthenticatedUser().getPrincipal();
			model.addAttribute(USER_ATTRIBUTE_KEY, user);	
			
		} catch(Exception ex){
			throw new ControllerRestException(SystemMessages.SYSTEM_ERROR, ex);
		}
		
		return TEMPLATE_LOGIN_EDIT;
	}
	
	/**
	 * <p>
	 * Saves a new user, in case of errors takes appropriate actions to inform the user. 
	 * </p>
	 * 
	 * @param user
	 * @param result
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@Secured(PermissionList.LOGIN.EDIT)
	@RequestMapping(value = "/login/update", method = RequestMethod.POST)
	String updateLogin(@ModelAttribute @Validated({PasswordGroup.class, FieldMatchGroup.class}) User user, BindingResult result, String oldPassword, Model model) {
		
		if (StringUtils.isBlank(oldPassword)) {
			model.addAttribute(USER_ATTRIBUTE_KEY, user);
			model.addAttribute(SystemMessages.MESSAGE_ATTRIBUTE, new Message("Senha inválida", Message.Type.WARNING));
			return TEMPLATE_LOGIN_EDIT;
		}
		
		if (result.hasErrors()) {
			model.addAttribute(USER_ATTRIBUTE_KEY, user);
		} else {
			try {
				
				User dataUser = userService.findOne(user.getId());
				userService.updatePassword(dataUser, oldPassword, user.getPassword());
				model.addAttribute(SystemMessages.MESSAGE_ATTRIBUTE, new Message(SystemMessages.ENTITY_SAVE_SUCCESS, Message.Type.INFO));
				
			} catch (ServiceException e) {
				logger.error("Error updatePassword user " + user.toString(), e);
				model.addAttribute(USER_ATTRIBUTE_KEY, user);
				model.addAttribute(SystemMessages.MESSAGE_ATTRIBUTE, new Message(e.getMessage(), Message.Type.DANGER));
			}
		}
		return TEMPLATE_LOGIN_EDIT;
	}
}
