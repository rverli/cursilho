package com.sicpa.thymeleaf.poc.aqualis.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.sicpa.thymeleaf.poc.aqualis.enumerator.AuditingOperationType;
import com.sicpa.thymeleaf.poc.aqualis.exception.ServiceException;
import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.Audit;
import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.Page;
import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.User;
import com.sicpa.thymeleaf.poc.aqualis.persistence.repository.AuditingRepository;
import com.sicpa.thymeleaf.poc.aqualis.persistence.repository.PageRepository;


@RunWith(MockitoJUnitRunner.class)
public class AuditingServiceImplTest {

	@InjectMocks
	private AuditingServiceImpl auditingServiceImpl;
	
	@Mock
	private AuditingRepository auditingRepository;

	@Mock
	private PageRepository pageRepository;

	@Before
	public void setUp(){
		user  = new User();
		page  = new Page();
		audit = new Audit(page , AuditingOperationType.EDIT, user , "host", "10.14.9.4");
	}
	
	private User user;
	
	private Page page;
	
	private Audit audit;
	
	@Test
	public void testSave() {

		auditingServiceImpl.save(audit);
		
		Mockito.verify(auditingRepository, Mockito.times(1)).saveAndFlush(audit);
	}
	
	@Test
	public void testSave_Exception() {
		
		Mockito.doThrow(Exception.class).when(auditingRepository).saveAndFlush(audit);
		
		auditingServiceImpl.save(audit);
		
		Mockito.verify(auditingRepository, Mockito.times(1)).saveAndFlush(audit);
	}
	
	@Test
	public void testfindByPageAndOperationAndUserAndHostnameAndDateBetweenInitialDateAndFinalDateValid() throws ServiceException {
		
		AuditingOperationType operation = AuditingOperationType.ADD;
		String userinfo = "user";
		String pagetitle = "Edição de Usuário";
		String pageaction = "Editar Usuário";
		String hostname = "";
		Date initialDate = null;
		Date finalDate = null;
		
		PageRequest pageable = new PageRequest(0, 1);
		List<Audit> lista = new ArrayList<>();
		lista.add(new Audit());
		org.springframework.data.domain.Page<Audit> pageList = new PageImpl<>(lista, pageable, lista.size());
		Mockito.when(auditingRepository.findByPageAndAuditingOperationTypeAndUserAndHostNameAndDateTimeBetween(
				userinfo, hostname, pagetitle, pageaction, operation, initialDate, finalDate, pageable)).thenReturn(pageList);
		
		org.springframework.data.domain.Page<Audit> auditList = auditingServiceImpl.
				findByPageAndOperationAndUserAndHostnameAndDateBetweenInitialDateAndFinalDate(
						pagetitle, pageaction, operation, userinfo,
						hostname, initialDate, finalDate, pageable);

		Assert.assertNotNull(auditList);
		Assert.assertEquals(1, auditList.getSize());
	}
	
	@Test(expected = ServiceException.class)
	@SuppressWarnings("unchecked")
	public void testfindByPageAndOperationAndUserAndHostnameAndDateBetweenInitialDateAndFinalDateThrowsException() throws ServiceException {
		
		AuditingOperationType operation = AuditingOperationType.ADD;
		String userinfo = "user";
		String pagetitle = "Edição de Usuário";
		String pageaction = "Editar Usuário";
		String hostname = "";
		Date initialDate = null;
		Date finalDate = null;
		PageRequest pageable = new PageRequest(0, 1);
		
		Mockito.when(auditingRepository.findByPageAndAuditingOperationTypeAndUserAndHostNameAndDateTimeBetween(
				userinfo, hostname, pagetitle, pageaction, operation, initialDate, finalDate, pageable)).thenThrow(Exception.class);
		
		auditingServiceImpl.findByPageAndOperationAndUserAndHostnameAndDateBetweenInitialDateAndFinalDate(
				pagetitle, pageaction, operation, userinfo,
				hostname, initialDate, finalDate, pageable);
	}
	
}
