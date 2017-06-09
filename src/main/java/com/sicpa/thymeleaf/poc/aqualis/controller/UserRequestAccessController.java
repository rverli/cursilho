package com.sicpa.thymeleaf.poc.aqualis.controller;

import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sicpa.thymeleaf.poc.aqualis.exception.ServiceException;
import com.sicpa.thymeleaf.poc.aqualis.messages.MessageHelper;
import com.sicpa.thymeleaf.poc.aqualis.messages.SystemMessages;
import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.User;
import com.sicpa.thymeleaf.poc.aqualis.service.UserRequestAccessService;
import com.sicpa.thymeleaf.poc.aqualis.service.UserService;
import com.sicpa.thymeleaf.poc.aqualis.utils.ProjectMapping;
import com.sicpa.thymeleaf.poc.aqualis.utils.StringUtils;
import com.sicpa.thymeleaf.poc.aqualis.utils.form.validators.fieldmatch.FieldMatchGroup;

/**
 * Provide access to the user requests access to the system
 * 
 * @author jcjunior
 *
 */
@Controller
public class UserRequestAccessController {

	public static final String ROOT = "redirect:/";

	public static final String REQUEST_NEW_PASSWORD_FORM = ProjectMapping.REQUEST_ACCESS.FORM;

	private static final Logger logger = Logger.getLogger(UserRequestAccessController.class);

	public static final String REDIRECT_LOGIN = "redirect:/login";

	@Autowired
	private UserRequestAccessService userRequestAccessService;
	
	@Autowired
	private UserService userService;

	@RequestMapping(value = ProjectMapping.REQUEST_ACCESS.BASE_MAPPING, method = RequestMethod.GET)
	public String forgotPassword(@RequestParam(value = "login") String login, RedirectAttributes redirectAttributes) {

		try {

			String loginEncoded = StringUtils.encodeStringUTF8(login);

			userRequestAccessService.forgotPasssword(StringUtils.decodeStringUTF8(loginEncoded));

		} catch (ServiceException | UnsupportedEncodingException | IllegalArgumentException e) {
			logger.error(e.getMessage(), e);
			MessageHelper.addErrorAttribute(redirectAttributes, e.getMessage());
			return REDIRECT_LOGIN;
		}

		MessageHelper.addSuccessAttribute(redirectAttributes, SystemMessages.USER_REQUEST_ACCESS_SUCCESS);
		return REDIRECT_LOGIN;

	}

	@RequestMapping(value = ProjectMapping.REQUEST_ACCESS.REQUEST_NEW_PASSWORD, method = RequestMethod.GET)
	String requestNewPassword(@PathVariable(value = "requestCode") String requestCode, Model model, RedirectAttributes redirectAttributes) {
		User user;

		try {
			user = userRequestAccessService.getUserByRequestCode(requestCode);
			loadObjectsToView(requestCode, model, user);

		} catch (ServiceException | IllegalArgumentException e) {
			throwErrorMessageToView("User request new password error", redirectAttributes, e);
			return ROOT;
		}

		return REQUEST_NEW_PASSWORD_FORM;
	}

	@RequestMapping(value = ProjectMapping.REQUEST_ACCESS.CREATE_NEW_PASSWORD, method = RequestMethod.POST)
	String createNewPassword(@ModelAttribute @Validated({FieldMatchGroup.class}) User user, BindingResult result, Model model,RedirectAttributes redirectAttributes, String requestCode) {
		
		if (result.hasErrors()) {
			loadObjectsToView(requestCode, model, user);
			return REQUEST_NEW_PASSWORD_FORM;
			
		} else {
			try {
				userService.updatePasswordByRequestCode(requestCode, user.getPassword());
				MessageHelper.addSuccessAttribute(redirectAttributes, SystemMessages.USER_REQUEST_ACCESS_NEW_PASSWORD);
				
			} catch (ServiceException | IllegalArgumentException e) {
				throwErrorMessageToView("Creating a new user password error", model, e);
				loadObjectsToView(requestCode, model, user);
				return REQUEST_NEW_PASSWORD_FORM;
			}
		}
		
		return ROOT;
	}

	private void throwErrorMessageToView(String logMessage, Model model, Exception e) {
		logger.error(logMessage, e);
		MessageHelper.addErrorAttribute(model, e.getMessage());
	}
	
	private void throwErrorMessageToView(String logMessage, RedirectAttributes redirectAttributes, Exception e) {
		logger.error(logMessage, e);
		MessageHelper.addErrorAttribute(redirectAttributes, e.getMessage());
	}
	
	private void loadObjectsToView(String requestCode, Model model, User user) {
		model.addAttribute("requestCode", requestCode);
		model.addAttribute("user", user);
	}
}
