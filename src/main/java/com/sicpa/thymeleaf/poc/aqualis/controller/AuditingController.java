package com.sicpa.thymeleaf.poc.aqualis.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sicpa.thymeleaf.poc.aqualis.enumerator.AuditingOperationType;
import com.sicpa.thymeleaf.poc.aqualis.environment.config.AppConfig;
import com.sicpa.thymeleaf.poc.aqualis.exception.MetadataException;
import com.sicpa.thymeleaf.poc.aqualis.exception.ServiceException;
import com.sicpa.thymeleaf.poc.aqualis.messages.MessageHelper;
import com.sicpa.thymeleaf.poc.aqualis.messages.SystemMessages;
import com.sicpa.thymeleaf.poc.aqualis.metadata.MetaEntity;
import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.Audit;
import com.sicpa.thymeleaf.poc.aqualis.security.PermissionList;
import com.sicpa.thymeleaf.poc.aqualis.service.AuditingService;
import com.sicpa.thymeleaf.poc.aqualis.service.PageService;
import com.sicpa.thymeleaf.poc.aqualis.utils.AqualisCombobox;
import com.sicpa.thymeleaf.poc.aqualis.utils.PageWrapper;
import com.sicpa.thymeleaf.poc.aqualis.utils.PageableUtil;
import com.sicpa.thymeleaf.poc.aqualis.utils.ProjectMapping;
import com.sicpa.thymeleaf.poc.aqualis.utils.form.converters.EnumConverter;

/**
 * Controller used for auditing reports 
 * 
 * @author ekoetsier
 *
 */
@Controller
@RequestMapping(ProjectMapping.AUDIT.BASE_MAPPING)
public class AuditingController {
	
	private static final Logger logger = Logger.getLogger(AuditingController.class);
	
	public static final String TEMPLATE_REPORT_PAGE = ProjectMapping.AUDIT.BASE_MAPPING + "/" +ProjectMapping.REPORT;
	public static final String PAGE_ACTIONS_BY_TITLE = ProjectMapping.AUDIT.PAGE_ACTIONS + "/{pageTitle}";
	
	@Autowired
	private AuditingService auditingService;
	
	@Autowired
	private PageService pageService;
	
	@Autowired
	private AppConfig appConfig;
	
	/**
	 * method to register a converter to the enum type
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(AuditingOperationType.class, new EnumConverter<>(AuditingOperationType.class));
	}	
	
	/**
	 * Auditing report
	 * 
	 * @param operationType 
	 * @param userinfo 
	 * @param hostname 
	 * @param pagetitle
	 * @param pageaction
	 * @param initialDate 
	 * @param finalDate 
	 * @param pageable 
	 * @param model
	 * @param request
	 * @return
	 */
	@Secured(PermissionList.USER.REPORT)
	@RequestMapping(value=ProjectMapping.REPORT, method=RequestMethod.GET)
	public String report( 
			@RequestParam(required=false) AuditingOperationType operationType, 
			@RequestParam(required=false) String userinfo, 
			@RequestParam(required=false) String hostname, 
			@RequestParam(required=false) String pagetitle, 
			@RequestParam(required=false) String pageaction, 
			@RequestParam(required=false) @DateTimeFormat(pattern="yyyy-MM-dd") Date initialDate, 
			@RequestParam(required=false) @DateTimeFormat(pattern="yyyy-MM-dd") Date finalDate, 
			Pageable pageable, 
			Model model, 
			HttpServletRequest request){
		
		MetaEntity<Audit> metadata = null;
		StringBuffer url = new StringBuffer();
		
		Sort sort = pageable.getSort();
		if (sort == null) {
			sort = new Sort(Sort.Direction.DESC, "dateTime");
		}
		Pageable newPageable 		= PageableUtil.getPageRequest(pageable, SystemMessages.ITEMS_PER_PAGE, sort);

		try {
			
			Page<Audit> pageAudit = auditingService.findByPageAndOperationAndUserAndHostnameAndDateBetweenInitialDateAndFinalDate(
					pagetitle, pageaction, operationType, userinfo,
					hostname, initialDate, finalDate, newPageable);			
			
			metadata  = new MetaEntity<>(Audit.class, appConfig);
			
			url = request.getRequestURL().append(request.getQueryString() != null ? "?"+request.getQueryString() : "");
			
			PageWrapper<Audit> page = new PageWrapper<>(pageAudit, url.toString(), metadata, sort);
			
			model.addAttribute("pages", pageService.findAllTitles() );
			model.addAttribute("pageActions", pagetitle != null ? pageService.findAllActionsByTitle(pagetitle) : pageService.findAllActionsByTitle(""));
			model.addAttribute("pageResult", pageAudit);
			model.addAttribute("page", page);
			
		} catch (ServiceException | MetadataException e) {
			logger.debug("erro ao obter dados para o relatorio de auditoria", e);
			createReturnExceptionList(model, newPageable, url.toString(), metadata, new PageImpl<>(new ArrayList<Audit>()), e.getMessage());
		} catch (Exception e) {
			logger.error("erro gerando relatorio de auditoria", e);
			createReturnExceptionList(model, newPageable, url.toString(), metadata, new PageImpl<>(new ArrayList<Audit>()), SystemMessages.SYSTEM_ERROR);	
		} 
		
		return TEMPLATE_REPORT_PAGE;
	}
	
	@Secured(PermissionList.USER.REPORT)
	@RequestMapping(value = PAGE_ACTIONS_BY_TITLE, method = RequestMethod.GET)
	public @ResponseBody List<AqualisCombobox> getPageActionsByTitle(Model model, @PathVariable("pageTitle") String pageTitle) {
	    
		List<AqualisCombobox> combos = new ArrayList<>();
		
	    try {
	    	
	    	List<String> pageActions = pageService.findAllActionsByTitle(pageTitle);
	    	
	    	for (String string : pageActions) {
	    		AqualisCombobox aqualisCombobox = new AqualisCombobox(string, string);
	    		combos.add(aqualisCombobox);
			}
	    	
		} catch (ServiceException e) {
			logger.debug("erro buscando a lista de acoes", e);
		}
	    
	    return combos;
	}
	
	private void createReturnExceptionList(Model model, Pageable pageable, String url,
			MetaEntity<Audit> metadata, Page<Audit> stampbycompanyPage, String message) {	
		
		MessageHelper.addErrorAttribute(model, message);
		PageWrapper<Audit> page = new PageWrapper<>(stampbycompanyPage, url, metadata, pageable.getSort());
		model.addAttribute("page", page);
	}
	
}
