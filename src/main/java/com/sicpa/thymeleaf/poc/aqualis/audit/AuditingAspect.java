package com.sicpa.thymeleaf.poc.aqualis.audit;

import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.sicpa.thymeleaf.poc.aqualis.enumerator.AuditingOperationType;
import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.Audit;
import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.Page;
import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.User;
import com.sicpa.thymeleaf.poc.aqualis.service.AsyncService;
import com.sicpa.thymeleaf.poc.aqualis.service.impl.PageServiceImpl;
import com.sicpa.thymeleaf.poc.aqualis.utils.AuthUtil;


/**
 * <p>
 * Audit by Aspect.
 * Provides an interceptor based on {@link Auditable} annotation.
 * </p>
 * @author Gsalomao
 */
@Aspect
@Component
public class AuditingAspect {
	
	private static final Logger logger = Logger.getLogger(AuditingAspect.class);

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private AsyncService asyncService;
	
	@Autowired
	private PageServiceImpl pageService;
	

	/**
	 * Called after annotated method returns, performs the audit action.
	 *   
	 * @param jp audited method
	 * @param auditable annotation
	 */
	@After("@annotation(auditable)")
	public void logAuditActivity(JoinPoint jp, Auditable auditable) {

		logger.info("Auditing information beginning" + jp.getSignature());

		try {
			
			Authentication auth = AuthUtil.getAuthenticatedUser();
			User user = (User) auth.getPrincipal();

			AuditingOperationType auditingOperationType = auditable.operationType();
			
			logger.info(request.getServletPath());

			String referer = request.getHeader(HttpHeaders.REFERER);
			//pegar somente nome da pagine tirar tudo depois antes do contexto
			referer = referer.substring(referer.indexOf(request.getContextPath()) + request.getContextPath().length() );
			Page page = pageService.findPageByPath(createPagePath(referer));
			
			Audit audit = new Audit(page , auditingOperationType, user, request.getRemoteHost(), request.getLocalAddr());
			
			logger.info( audit.toString() );
			
			logger.info("Auditing information: " + audit.toString());

			asyncService.doAsync(audit);
			
		} catch (Exception e) {
			logger.error("Erro ao realizar log da chamada", e);
		}
	
	}


	/**
	 * <p>
	 * Cria o caminho da pagina caso que tiver um path variable como por exemplo 
	 * \/user/add/1
	 * </p>
	 * @param calledPath
	 * @return
	 */
	private String createPagePath(String calledPath) {
		String path = "";
		StringTokenizer st = new StringTokenizer(calledPath, "/");
		while (st.hasMoreTokens()) {
			String element = st.nextElement().toString();
			if( !StringUtils.isNumeric( element )){
				path += "/" + element ;
			}
		}

		return path;
	}

}
