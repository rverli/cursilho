package com.sicpa.thymeleaf.poc.aqualis.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.groups.Default;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sicpa.thymeleaf.poc.aqualis.enumerator.AddressType;
import com.sicpa.thymeleaf.poc.aqualis.enumerator.ProfileType;
import com.sicpa.thymeleaf.poc.aqualis.environment.config.AppConfig;
import com.sicpa.thymeleaf.poc.aqualis.exception.ControllerRestException;
import com.sicpa.thymeleaf.poc.aqualis.exception.MetadataException;
import com.sicpa.thymeleaf.poc.aqualis.exception.ServiceException;
import com.sicpa.thymeleaf.poc.aqualis.messages.Message;
import com.sicpa.thymeleaf.poc.aqualis.messages.ObjectMapperUtil;
import com.sicpa.thymeleaf.poc.aqualis.messages.SystemMessages;
import com.sicpa.thymeleaf.poc.aqualis.metadata.MetaEntity;
import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.Retreat;
import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.RetreatHouse;
import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.User;
import com.sicpa.thymeleaf.poc.aqualis.security.PermissionList;
import com.sicpa.thymeleaf.poc.aqualis.service.RetreatHouseService;
import com.sicpa.thymeleaf.poc.aqualis.service.RetreatService;
import com.sicpa.thymeleaf.poc.aqualis.service.UserService;
import com.sicpa.thymeleaf.poc.aqualis.utils.PageWrapper;
import com.sicpa.thymeleaf.poc.aqualis.utils.PageableUtil;
import com.sicpa.thymeleaf.poc.aqualis.utils.ProjectMapping;
import com.sicpa.thymeleaf.poc.aqualis.utils.form.converters.EnumConverter;
import com.sicpa.thymeleaf.poc.aqualis.utils.form.validators.fieldmatch.FieldMatchGroup;

@Controller
@RequestMapping(ProjectMapping.RETREAT.BASE_MAPPING)
public class RetreatController extends AbstractCrudController<Retreat, Long>{

	private static final Logger logger = Logger.getLogger(RetreatController.class);
	
	private static final String ERROR_OBTAINING_DATA = "Error obtaining data";
	private static final String RETREAT_ATTRIBUTE_KEY = "retreat";
	private static final String RETREAT_HOUSE_ATTRIBUTE_KEY = "retreatHouses";
	public static final String TEMPLATE_RETREAT_DETAILS = ProjectMapping.RETREAT.BASE_MAPPING+"/details";
	public static final String TEMPLATE_RETREAT_ADMIN = ProjectMapping.RETREAT.BASE_MAPPING + "/" + ProjectMapping.ADMIN;
	public static final String TEMPLATE_RETREAT_LIST = ProjectMapping.RETREAT.BASE_MAPPING + "/" + ProjectMapping.LIST;
	public static final String TEMPLATE_RETREAT_ADD =  ProjectMapping.RETREAT.BASE_MAPPING + "/" + ProjectMapping.ADD;
	public static final String TEMPLATE_RETREAT_EDIT = ProjectMapping.RETREAT.BASE_MAPPING + "/" + ProjectMapping.EDIT_PAGE;
	public static final String REDIRECT_RETREAT_ADMIN_MAPPING = "redirect:/" + ProjectMapping.RETREAT.BASE_MAPPING+"/" + ProjectMapping.ADMIN;
	public static final String TEMPLATE_RETREAT_REPORT = ProjectMapping.RETREAT.BASE_MAPPING + "/" + ProjectMapping.REPORT;;

	@Autowired
	private UserService userService;
	
	@Autowired
	private RetreatService retreatService;

	@Autowired
	private RetreatHouseService retreatHouseService;
	
	@Autowired
	private AppConfig appConfig;
	
	public List<RetreatHouse> allRetreatHouses;
	
	@Autowired
	RetreatController(RetreatService service) {
        super(service, RETREAT_ATTRIBUTE_KEY);
        this.retreatService = service;
    }

	private User user;
	
	/**
	 * method to register a converter to the enum type
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(AddressType.class, new EnumConverter<>(AddressType.class));
	}	
		
	@Secured({PermissionList.RETREAT.ADD})
	@RequestMapping(ProjectMapping.ADD)
	String newRetreat(Model model) throws ControllerRestException {
		
		Retreat retreat = new Retreat();
		
		retreat.setActive(true);
		
		model.addAttribute(RETREAT_HOUSE_ATTRIBUTE_KEY, this.getAllRetreatHouses());
		model.addAttribute(RETREAT_ATTRIBUTE_KEY, retreat);
		
		return TEMPLATE_RETREAT_ADD;
	}
	
	@Secured({PermissionList.RETREAT.ADD})
	@RequestMapping(value = ProjectMapping.SAVE, method = RequestMethod.POST)
	String save(@ModelAttribute @Validated({Default.class, FieldMatchGroup.class}) Retreat retreat, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
		return super.save(retreat, result, model, redirectAttributes);
	}
	
	private List<RetreatHouse> getAllRetreatHouses() throws ControllerRestException {
		
		try {
			allRetreatHouses = retreatHouseService.findAllRetreatHouses();
		} catch(ServiceException se){
			throw new ControllerRestException(SystemMessages.USER_UPDATE_ERRO, se);
		} catch(Exception ex){
			throw new ControllerRestException(SystemMessages.SYSTEM_ERROR, ex);
		}
		return allRetreatHouses;
	}
	
	/**
	 * <p>
	 * Shows page with a retreat to edited with the id used
	 * </p>
	 * @param id
	 * @param model
	 * @return
	 * @throws ControllerRestException
	 */
	@Secured({PermissionList.RETREAT.EDIT})
	@RequestMapping(value = ProjectMapping.EDIT, method = RequestMethod.GET)
	String editRetreat(@PathVariable("id") Long id, Model model) throws ControllerRestException {
		
		try {
			Retreat retreat = retreatService.findOne(id);
			
			model.addAttribute(RETREAT_HOUSE_ATTRIBUTE_KEY, this.getAllRetreatHouses());
			model.addAttribute(RETREAT_ATTRIBUTE_KEY, retreat);
			
		} catch(ServiceException se){
			throw new ControllerRestException(SystemMessages.RETREAT_UPDATE_ERRO, se);
		} catch(Exception ex){
			throw new ControllerRestException(SystemMessages.SYSTEM_ERROR, ex);
		} 
		return TEMPLATE_RETREAT_EDIT;
	}
	

	/**
	 * <p>
	 * Search list companies by properties passed to the method returns to the
	 * admin list were actions like add, update delete can be taken
	 * </p>
	 * 
	 * @param ...
	 * @param active
	 * @param model
	 * @param pageable
	 * @return
	 * @throws ControllerRestException
	 */
	@Secured(PermissionList.RETREAT.ADMIN)
	@RequestMapping(ProjectMapping.ADMIN)
	String adminList(
			@RequestParam(value = "number", required = false) Long number,
			@RequestParam(value = "coordinator", required = false) String coordinator,
			@RequestParam(value = "selectRetreatHouse", required = false) String selectRetreatHouse,		
			@RequestParam(value = "active", required = false) Boolean active, ModelMap model, Pageable pageable, HttpServletRequest request)
			throws ControllerRestException {

		this.populateUserSession( request );
		
		listRetreats(number, coordinator, selectRetreatHouse, active, model, pageable, true, request);

		return TEMPLATE_RETREAT_ADMIN;
	}

	@Secured(PermissionList.RETREAT.EDIT)
	@RequestMapping(value = ProjectMapping.UPDATE, method = RequestMethod.POST)
	public String update(@ModelAttribute @Validated({Default.class}) Retreat retreat, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
		return super.update(retreat, result, model, redirectAttributes);
	}
	
	@Secured(PermissionList.RETREAT.DETAILS)
	@RequestMapping(value = ProjectMapping.DETAILS, method = RequestMethod.GET)
	String details(@PathVariable("id") Long id, Model model) throws JsonProcessingException, ControllerRestException {
		
		try {
			Retreat retreat = retreatService.findOne(id);

			model.addAttribute(RETREAT_HOUSE_ATTRIBUTE_KEY, this.getAllRetreatHouses());
			model.addAttribute(RETREAT_ATTRIBUTE_KEY, retreat);
		} catch(ServiceException se){
			logger.error(SystemMessages.ENTITY_DETAIL_ERROR, se);
			throw new ControllerRestException(ObjectMapperUtil.convertJsonMessage(SystemMessages.ENTITY_DETAIL_ERROR), se);
		} catch(Exception ex){
			logger.error(SystemMessages.SYSTEM_ERROR, ex);
			throw new ControllerRestException(ObjectMapperUtil.convertJsonMessage(SystemMessages.SYSTEM_ERROR), ex);
		} 

		return  TEMPLATE_RETREAT_DETAILS;
	}
	
	@Secured(PermissionList.RETREAT.ACTIVE_INACTIVE)
	@RequestMapping(value = ProjectMapping.ACTIVE_INACTIVE, method=RequestMethod.PUT)
	ResponseEntity<String> activate(@RequestParam(value="id") Long id) throws ControllerRestException {
		return super.activate(id);
	}
	
	@Secured(PermissionList.RETREAT.DELETE)
	@RequestMapping(value = ProjectMapping.DELETE, method=RequestMethod.DELETE, produces=MediaType.APPLICATION_JSON_VALUE)	
	ResponseEntity<String> delete(@PathVariable(value="id") Long id) throws ControllerRestException {
		return super.delete(id);
	}
	
	/**
	 * <p>
	 * Search list companies by properties passed to the method returns to the
	 * public list were no actions can be taken
	 * </p>
	 * 
	 * @param ...
	 * @param active
	 * @param model
	 * @param pageable
	 * @return
	 * @throws ControllerRestException
	 */
	@Secured(PermissionList.RETREAT.LIST)
	@RequestMapping(ProjectMapping.LIST)
	String list(
			@RequestParam(value = "number", required = false) Long number,
			@RequestParam(value = "coordinator", required = false) String coordinator,
			@RequestParam(value = "selectRetreatHouse", required = false) String selectRetreatHouse,
			@RequestParam(value = "active", required = false) Boolean active, ModelMap model, Pageable pageable, HttpServletRequest request){

		this.populateUserSession( request );
		
		listRetreats(number, coordinator, selectRetreatHouse, active, model, pageable, false, request);

		return TEMPLATE_RETREAT_LIST;
	}
	
	/**
	 * <p>
	 * Search list companies by properties passed to the method returns to the
	 * public list were no actions can be taken
	 * </p>
	 * 
	 * @param ...
	 * @param active
	 * @param model
	 * @param pageable
	 * @return
	 * @throws ControllerRestException
	 */
	@Secured(PermissionList.RETREAT.REPORT)
	@RequestMapping(ProjectMapping.REPORT)
	String report(
			@RequestParam(value = "number", required = false) Long number,
			@RequestParam(value = "coordinator", required = false) String coordinator,
			@RequestParam(value = "selectRetreatHouse", required = false) String selectRetreatHouse,
			@RequestParam(value = "active", required = false) Boolean active, ModelMap model, Pageable pageable, HttpServletRequest request){

		listRetreats(number, coordinator, selectRetreatHouse, active, model, pageable, false, request);

		return TEMPLATE_RETREAT_REPORT;
	}

	private void populateUserSession( HttpServletRequest request ) {
		
		Long idUser = (Long) request.getSession().getAttribute("user");
		
		user = new User( idUser );
	}
	
	/**
	 * <p>
	 * Queries companies by the parameters
	 * </p>
	 * 
	 * @param ...
	 * @param active a search parameter
	 * @param model
	 * @param pageable
	 * @param isAdministrative
	 * @throws ControllerRestException
	 */
	private void listRetreats(Long number, String coordinator, String nameRetreatHouse, Boolean active, ModelMap model,
			Pageable pageable, boolean isAdministrative, HttpServletRequest request) {

		StringBuffer url = new StringBuffer();
		MetaEntity<Retreat> metadata	= null;
		Pageable newPageable 			= PageableUtil.getPageRequest(pageable, SystemMessages.ITEMS_PER_PAGE);
		Page<Retreat> cPage 		= new PageImpl<>(new ArrayList<Retreat>());
		
		Long idRetreat = null;
		
		try {
			metadata	= new MetaEntity<>(Retreat.class, appConfig);

			url = request.getRequestURL().append(request.getQueryString() != null ? "?"+request.getQueryString() : "");

			User userLocal = userService.findUserById( user.getId() );
			
			if ( userLocal != null && userLocal.getProfiles() != null ) {
				
				if ( userLocal.getProfiles().size() == 1 
						&& ( userLocal.getProfiles().get(0).getName().equalsIgnoreCase( ProfileType.PROFILE_SECRETARY.toString() ) 
								|| userLocal.getProfiles().get(0).getName().equalsIgnoreCase( ProfileType.PROFILE_COORDINATOR.toString() ) ) ) {
					
					if ( userLocal.getRetreat() != null ) {
						idRetreat = userLocal.getRetreat().getId();
					}
				}
			}
			
			if ( idRetreat != null ) {
				cPage = retreatService.findRetreatByIdPage( idRetreat, newPageable );
			} else {
				cPage = retreatService.findByNumberAndCoordinatorAndRetreatHouseAndActiveContaining(number, coordinator, nameRetreatHouse, active, newPageable);
			}

			PageWrapper<Retreat> page = new PageWrapper<>(cPage, url.toString(), metadata, pageable.getSort());

			model.addAttribute("page", page);
			
			model.addAttribute(RETREAT_HOUSE_ATTRIBUTE_KEY, this.getAllRetreatHouses());
			
		} catch (ServiceException | MetadataException me) {
			logger.error(ERROR_OBTAINING_DATA, me);
			createReturnExceptionList(model, pageable, url.toString(), metadata, cPage, me.getMessage());
		} catch (Exception ex) {
			logger.error(ERROR_OBTAINING_DATA, ex);
			createReturnExceptionList(model, pageable, url.toString(), metadata, cPage, SystemMessages.SYSTEM_ERROR);
		}
	}
	
	private void createReturnExceptionList(ModelMap model, Pageable pageable, String url,
			MetaEntity<Retreat> metadata, Page<Retreat> cPage, String message) {
		
		model.addAttribute(SystemMessages.MESSAGE_ATTRIBUTE, new Message(message, Message.Type.DANGER));
		PageWrapper<Retreat> page = new PageWrapper<>(cPage, url, metadata, pageable.getSort());
		model.addAttribute("page", page);
	}
	
	
}
