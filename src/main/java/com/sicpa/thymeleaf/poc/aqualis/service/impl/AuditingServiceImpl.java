package com.sicpa.thymeleaf.poc.aqualis.service.impl;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sicpa.thymeleaf.poc.aqualis.enumerator.AuditingOperationType;
import com.sicpa.thymeleaf.poc.aqualis.exception.ServiceException;
import com.sicpa.thymeleaf.poc.aqualis.messages.SystemMessages;
import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.Audit;
import com.sicpa.thymeleaf.poc.aqualis.persistence.repository.AuditingRepository;
import com.sicpa.thymeleaf.poc.aqualis.service.AuditingService;

/**
 * Class Responsible for Auditing operations and reports 
 * 
 */
@Service
public class AuditingServiceImpl implements AuditingService {

	private static final Logger logger = Logger.getLogger(AuditingServiceImpl.class);
	
	@Autowired
	private AuditingRepository auditingRepository;

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void save(Audit audit) {
	
		try {
			auditingRepository.saveAndFlush(audit);
		} catch (Exception e) {
			logger.error("Erro ao salvar entity de auditoria" + audit , e);
		}

	}

	@Override
	public Page<Audit> findByPageAndOperationAndUserAndHostnameAndDateBetweenInitialDateAndFinalDate(
			String pagetitle, String pageaction, AuditingOperationType operation, String userinfo,
			String hostname, Date initialDate, Date finalDate, Pageable pageable) throws ServiceException {
		
		Page<Audit> auditList;
		
		try {
			auditList = auditingRepository.findByPageAndAuditingOperationTypeAndUserAndHostNameAndDateTimeBetween(
					userinfo, hostname, pagetitle, pageaction, operation, initialDate, finalDate, pageable);
		} catch (Exception e) {
			logger.error("Erro ao listar auditoria", e);
			throw new ServiceException(SystemMessages.SYSTEM_ERROR, e);
		}
		
		
		return auditList;
	}

}
