package com.sicpa.thymeleaf.poc.aqualis.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.Audit;
import com.sicpa.thymeleaf.poc.aqualis.service.AuditingService;

@RunWith(MockitoJUnitRunner.class)
public class AuditingAsyncServiceImplTest {
	
	@InjectMocks
	private AuditingAsyncServiceImpl auditingAsyncService;
	
	@Mock
	private AuditingService auditingService;

	
	@Test
	public void testDoAsync() {
		Audit audit = null;
		
		auditingAsyncService.doAsync( audit );
		
		Mockito.verify(auditingService, Mockito.times(1)).save(audit);
	}
	
	@Test
	public void testDoAsync_Exception() {
		Audit audit = null;
		
		Mockito.doThrow(Exception.class).when( auditingService ).save(audit);
		
		auditingAsyncService.doAsync( audit );
		
		Mockito.verify(auditingService, Mockito.times(1)).save(audit);
	}


}
