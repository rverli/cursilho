package com.sicpa.thymeleaf.poc.aqualis.utils;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;

import com.sicpa.thymeleaf.poc.aqualis.enumerator.AuditingOperationType;
import com.sicpa.thymeleaf.poc.aqualis.exception.ServiceException;
import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.Audit;
import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.Page;
import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.User;
import com.sicpa.thymeleaf.poc.aqualis.service.AuditingService;
import com.sicpa.thymeleaf.poc.aqualis.service.PageService;

@RunWith(MockitoJUnitRunner.class)
public class AuditUtilTest {
	
	@InjectMocks
	private AuditUtil auditUtil;
	
	@Mock
	private PageService pageService;

	@Mock
	private AuditingService auditingService;
	
	@Test
	public void testAuditOperationWithValidParameters() throws ServiceException {
		
		Authentication auth = Mockito.mock(Authentication.class);
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		
		Mockito.when(auth.getPrincipal()).thenReturn(new User());
		
		Page page = Mockito.mock(Page.class);
		Mockito.when(pageService.findPageByPath("/login")).thenReturn(page);
		
		auditUtil.auditOperation(request, auth, "/login", AuditingOperationType.LOGIN);
		
		Mockito.verify(auditingService).save((Audit)Mockito.any());
	}

	@Test
	public void testAuditOperationWithNullPage() throws ServiceException {

		Authentication auth = Mockito.mock(Authentication.class);
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		
		Mockito.when(auth.getPrincipal()).thenReturn(new User());
		
		Mockito.when(pageService.findPageByPath("/login")).thenReturn(null);

		auditUtil.auditOperation(request, auth, null, AuditingOperationType.LOGIN);
		
		Mockito.verify(auditingService, Mockito.never()).save((Audit)Mockito.any());
	}

	@Test
	public void testAuditOperationWithNullPath() {

		Authentication auth = Mockito.mock(Authentication.class);
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		
		Mockito.when(auth.getPrincipal()).thenReturn(new User());
		
		auditUtil.auditOperation(request, auth, null, AuditingOperationType.LOGIN);
		
		Mockito.verify(auditingService, Mockito.never()).save((Audit)Mockito.any());
	}

	@Test
	public void testAuditOperationWithEmptyPath() {

		Authentication auth = Mockito.mock(Authentication.class);
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		
		Mockito.when(auth.getPrincipal()).thenReturn(new User());
		
		auditUtil.auditOperation(request, auth, "", AuditingOperationType.LOGIN);
		
		Mockito.verify(auditingService, Mockito.never()).save((Audit)Mockito.any());
	}
	
	@Test
	public void testAuditOperationWithNullUser() throws ServiceException {

		Authentication auth = Mockito.mock(Authentication.class);
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		
		Mockito.when(auth.getPrincipal()).thenReturn(null);

		Page page = Mockito.mock(Page.class);
		Mockito.when(pageService.findPageByPath("/login")).thenReturn(page);
		
		auditUtil.auditOperation(request, auth, "/login", AuditingOperationType.LOGIN);
		
		Mockito.verify(auditingService, Mockito.never()).save((Audit)Mockito.any());
	}

	@Test
	public void testAuditOperationWithNullAuthentication() throws ServiceException {

		Authentication auth = null;
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		
		Page page = Mockito.mock(Page.class);
		Mockito.when(pageService.findPageByPath("/login")).thenReturn(page);
		
		auditUtil.auditOperation(request, auth, "/login", AuditingOperationType.LOGIN);
		
		Mockito.verify(auditingService, Mockito.never()).save((Audit)Mockito.any());
	}
	
	@Test
	public void testAuditOperationWithNullAuditOperationType() throws ServiceException {

		Authentication auth = Mockito.mock(Authentication.class);
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		
		Mockito.when(auth.getPrincipal()).thenReturn(new User());

		Page page = Mockito.mock(Page.class);
		Mockito.when(pageService.findPageByPath("/login")).thenReturn(page);

		auditUtil.auditOperation(request, auth, "/login", null);
		
		Mockito.verify(auditingService, Mockito.never()).save((Audit)Mockito.any());
	}
	
	@Test
	public void testAuditOperationThrowException() throws ServiceException {

		Authentication auth = Mockito.mock(Authentication.class);
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		
		Mockito.when(auth.getPrincipal()).thenReturn(new User());

		Page page = Mockito.mock(Page.class);
		Mockito.when(pageService.findPageByPath("/login")).thenReturn(page);
		
		Mockito.doThrow(ServiceException.class).when(auditingService).save((Audit) Mockito.any());
		
		auditUtil.auditOperation(request, auth, "/login", AuditingOperationType.LOGIN);
		
		Mockito.verify(auditingService).save((Audit)Mockito.any());
	}
	
	@Test
	public void testAuditOperationWithRequestParameterNull() throws ServiceException {
		
		Authentication auth = Mockito.mock(Authentication.class);
		HttpServletRequest request = null;
		
		Mockito.when(auth.getPrincipal()).thenReturn(new User());
		
		Page page = Mockito.mock(Page.class);
		Mockito.when(pageService.findPageByPath("/login")).thenReturn(page);
		
		auditUtil.auditOperation(request, auth, "/login", AuditingOperationType.LOGIN);
		
		Mockito.verify(auditingService, Mockito.never()).save((Audit)Mockito.any());
	}

	@Test
	public void testAuditOperationWithAllParametersNullLessThePathParameter() throws ServiceException {
		
		Authentication auth = null;
		HttpServletRequest request = null;
		
		Mockito.when(pageService.findPageByPath("/login")).thenReturn(null);
		
		auditUtil.auditOperation(request, auth, "/login", null);
		
		Mockito.verify(auditingService, Mockito.never()).save((Audit)Mockito.any());
	}
	
}
