package com.sicpa.thymeleaf.poc.aqualis.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.Audit;
import com.sicpa.thymeleaf.poc.aqualis.service.AsyncService;
import com.sicpa.thymeleaf.poc.aqualis.service.AuditingService;

/**
 * Is Called Async to perform the auditing used only form the Auditing of the methods
 * @author Gsalomao
 */
@Service
public final class AuditingAsyncServiceImpl implements AsyncService {

	private static final Logger logger = Logger.getLogger(AuditingAsyncServiceImpl.class);
	
	@Autowired(required=true)
	private AuditingService auditingService; 
	
	/**
	 * Async method calls the service which does the auditing 
	 */
	@Async
	@Override
	public void doAsync(Audit audit) {
		try {
			
			auditingService.save(audit);

		} catch(Exception e) {
			logger.error("Error while logging audit: " + audit, e);
		}
	}
}
