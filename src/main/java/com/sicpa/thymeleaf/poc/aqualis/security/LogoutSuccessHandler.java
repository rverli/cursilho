package com.sicpa.thymeleaf.poc.aqualis.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import com.sicpa.thymeleaf.poc.aqualis.enumerator.AuditingOperationType;
import com.sicpa.thymeleaf.poc.aqualis.utils.AuditUtil;

/**
 * Used to audit logout operation.
 * @author lrosa1
 *
 */
@Component
public class LogoutSuccessHandler implements LogoutHandler {

	@Autowired
	private AuditUtil auditUtil;
	
	/**
	 * Intercepts and audits the logout success operation
	 */
	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		auditUtil.auditOperation(request, authentication, "/logout", AuditingOperationType.LOGOUT);
	}

}
