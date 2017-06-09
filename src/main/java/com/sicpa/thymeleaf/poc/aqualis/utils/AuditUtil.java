package com.sicpa.thymeleaf.poc.aqualis.utils;

import javax.servlet.http.HttpServletRequest;

import org.hsqldb.lib.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.sicpa.thymeleaf.poc.aqualis.enumerator.AuditingOperationType;
import com.sicpa.thymeleaf.poc.aqualis.exception.ServiceException;
import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.Audit;
import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.Page;
import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.User;
import com.sicpa.thymeleaf.poc.aqualis.service.AuditingService;
import com.sicpa.thymeleaf.poc.aqualis.service.PageService;

/**
 * Utility class to audit operations manually
 * @author lrosa1
 *
 */
@Component
public class AuditUtil {

	private Logger logger = LoggerFactory.getLogger(AuditUtil.class);

	@Autowired
	private PageService pageService;

	@Autowired
	private AuditingService auditingService;

	/**
	 * 
	 * @param request
	 * @param auth
	 * @param path
	 * @param auditingOperationType
	 */
	public void auditOperation(HttpServletRequest request, Authentication auth, String path,
			AuditingOperationType auditingOperationType) {
		try {
			if (!StringUtil.isEmpty(path)) {
				Page page = pageService.findPageByPath(path);
				if (page != null && existingLoggedUser(auth) && auditingOperationType != null && request != null) {
					Audit audit = new Audit(page, auditingOperationType, (User) auth.getPrincipal(),
							request.getRemoteHost(), request.getRemoteAddr());
					auditingService.save(audit);
				}
			}
		} catch (ServiceException e) {
			logger.error("audit operation error", e);
		}
	}
	
	private boolean existingLoggedUser(Authentication auth) {
		return auth != null && auth.getPrincipal() != null;
	}
}
