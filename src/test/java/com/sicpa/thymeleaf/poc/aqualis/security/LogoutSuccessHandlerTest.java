package com.sicpa.thymeleaf.poc.aqualis.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;

import com.sicpa.thymeleaf.poc.aqualis.enumerator.AuditingOperationType;
import com.sicpa.thymeleaf.poc.aqualis.utils.AuditUtil;

@RunWith(MockitoJUnitRunner.class)
public class LogoutSuccessHandlerTest {
	
	@InjectMocks
	private LogoutSuccessHandler handler;
	
	@Mock
	private AuditUtil auditUtil;
	
	@Test
	public void testAuditOperationOnLogoutSuccess() throws IOException, ServletException {
		
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		Authentication authentication = Mockito.mock(Authentication.class);

		handler.logout(request, response, authentication);
		
		Mockito.verify(auditUtil).auditOperation(request, authentication, "/logout", AuditingOperationType.LOGOUT);
	}
}
