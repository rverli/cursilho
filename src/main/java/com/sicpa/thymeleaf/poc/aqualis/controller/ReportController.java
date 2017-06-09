package com.sicpa.thymeleaf.poc.aqualis.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sicpa.thymeleaf.poc.aqualis.enumerator.ProfileType;
import com.sicpa.thymeleaf.poc.aqualis.exception.ControllerRestException;
import com.sicpa.thymeleaf.poc.aqualis.exception.ServiceException;
import com.sicpa.thymeleaf.poc.aqualis.messages.SystemMessages;
import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.Retreat;
import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.User;
import com.sicpa.thymeleaf.poc.aqualis.security.PermissionList;
import com.sicpa.thymeleaf.poc.aqualis.service.ReportService;
import com.sicpa.thymeleaf.poc.aqualis.service.RetreatService;
import com.sicpa.thymeleaf.poc.aqualis.service.UserService;
import com.sicpa.thymeleaf.poc.aqualis.utils.ProjectMapping;

@Controller
@RequestMapping(ProjectMapping.REPORT_.BASE_MAPPING)
public class ReportController {

	private static final Logger logger = Logger.getLogger(ReportController.class);
	
	private static final String RETREAT_ATTRIBUTE_KEY = "retreats";
	public static final String TEMPLATE_REPORT_ADMIN = ProjectMapping.REPORT_.BASE_MAPPING + "/" + ProjectMapping.ADMIN;

	@Autowired
	private ReportService reportService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RetreatService retreatService;
	
	private User user;
	private List<Retreat> allRetreats = null;
	
	@Secured(PermissionList.REPORT.ADMIN)
	@RequestMapping(ProjectMapping.ADMIN)
	String adminList(
			@RequestParam(value = "numberRetreat", required = false) Long idRetreat,			
			ModelMap model, HttpServletRequest request, HttpServletResponse response)
			throws ControllerRestException, ServiceException {

		Long idUser = (Long) request.getSession().getAttribute("user");
		
		user = new User( idUser );
		
		model.addAttribute(RETREAT_ATTRIBUTE_KEY, this.getAllRetreats());

		if ( idRetreat == null || idRetreat == -1 ) {
			return TEMPLATE_REPORT_ADMIN;
		} else {
			this.generateJasper( idRetreat, response );
			return TEMPLATE_REPORT_ADMIN;
		}
	}
	
	private void generateJasper( Long idRetreat, HttpServletResponse response ) throws ServiceException {
		
		/*List<Cursilhista> cursilhistas = reportService.findByIdRetreat( idRetreat );
	    
		if ( cursilhistas == null || cursilhistas.size() == 0 ) {
			return;
		}
		
		try {
			JRDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(cursilhistas);
			
			InputStream jasperStream = this.getClass().getResourceAsStream("/jasper/retreatReport.jasper");
			
		    Map<String,Object> params = new HashMap<>();
		    
		    params.put("listParam", cursilhistas);
		    
		    JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
		    
		    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, beanCollectionDataSource);

		    response.setContentType("application/x-pdf");
		    response.setHeader("Content-disposition", "inline; filename=Mirante.pdf");
		    
		    OutputStream outStream = response.getOutputStream();
		    
		    JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
			
		    outStream.flush();
		    outStream.close();
		    
		} catch (JRException e) {
			logger.error("Erro ao gerar relatório", e);
		} catch (IOException e) {
			logger.error("Erro ao gerar relatório", e);
		}*/
	}
	
	private List<Retreat> getAllRetreats() throws ControllerRestException {
		
		try {
			User userLocal = userService.findUserById( user.getId() );
			
			if ( userLocal != null && userLocal.getProfiles() != null ) {
				
				if ( userLocal.getProfiles().size() == 1 
						&& ( userLocal.getProfiles().get(0).getName().equalsIgnoreCase( ProfileType.PROFILE_SECRETARY.toString() ) 
								|| userLocal.getProfiles().get(0).getName().equalsIgnoreCase( ProfileType.PROFILE_COORDINATOR.toString() ) ) ) {
					
					Long idRetreat = null;
					
					if ( userLocal.getRetreat() != null ) {
						idRetreat = userLocal.getRetreat().getId();
					} 
						
					Retreat retreat = null;
					
					if ( idRetreat != null ) {
						retreat = retreatService.findRetreatById( idRetreat );
					} 
					
					if ( retreat != null ) {
						allRetreats = new ArrayList<>();
						allRetreats.add( retreat );
					} else {
						allRetreats = retreatService.findAllRetreats();
					}
					
					return allRetreats;
				}
			}
			
			allRetreats = retreatService.findAllRetreats();
		} catch(ServiceException se){
			throw new ControllerRestException(SystemMessages.USER_UPDATE_ERRO, se);
		} catch(Exception ex){
			throw new ControllerRestException(SystemMessages.SYSTEM_ERROR, ex);
		}
		return allRetreats;
	}
}
