package com.sicpa.thymeleaf.poc.aqualis.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.sicpa.thymeleaf.poc.aqualis.enumerator.AuditingOperationType;
import com.sicpa.thymeleaf.poc.aqualis.utils.AuditUtil;

/**
 * Used to audit login operation.
 * @author lrosa1
 *
 */
@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	
	@Autowired
	private AuditUtil auditUtil;
	
	/**
	 * Intercepts and audits the login success operation
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		auditUtil.auditOperation(request, authentication, "/login", AuditingOperationType.LOGIN);
		
		Long user = (Long) request.getSession().getAttribute("user");
		
		request.setAttribute("user", user);
		
		super.onAuthenticationSuccess(request, response, authentication);
	}
	
}
