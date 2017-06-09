package com.sicpa.thymeleaf.poc.aqualis.controller;

import java.util.ArrayList;
import java.util.Date;
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
import com.sicpa.thymeleaf.poc.aqualis.messages.MessageHelper;
import com.sicpa.thymeleaf.poc.aqualis.messages.ObjectMapperUtil;
import com.sicpa.thymeleaf.poc.aqualis.messages.SystemMessages;
import com.sicpa.thymeleaf.poc.aqualis.metadata.MetaEntity;
import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.Address;
import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.Cursilhista;
import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.Retreat;
import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.User;
import com.sicpa.thymeleaf.poc.aqualis.security.PermissionList;
import com.sicpa.thymeleaf.poc.aqualis.service.CursilhistaService;
import com.sicpa.thymeleaf.poc.aqualis.service.RetreatService;
import com.sicpa.thymeleaf.poc.aqualis.service.UserService;
import com.sicpa.thymeleaf.poc.aqualis.utils.PageWrapper;
import com.sicpa.thymeleaf.poc.aqualis.utils.PageableUtil;
import com.sicpa.thymeleaf.poc.aqualis.utils.ProjectMapping;
import com.sicpa.thymeleaf.poc.aqualis.utils.form.converters.EnumConverter;
import com.sicpa.thymeleaf.poc.aqualis.utils.form.validators.fieldmatch.FieldMatchGroup;

/**
 * <p>
 * Controller for operation on the {@link Candidate} entity like list, update add
 * and delete
 * </p>
 * 
 */
@Controller
@RequestMapping(ProjectMapping.CANDIDATE.BASE_MAPPING)
public class CandidateController extends AbstractCrudController<Cursilhista, Long>{

	private static final Logger logger = Logger.getLogger(CandidateController.class);
	
	private static final String ERROR_OBTAINING_DATA = "Error obtaining data";
	private static final String CANDIDATE_ATTRIBUTE_KEY = "candidate";
	private static final String RETREAT_ATTRIBUTE_KEY = "retreats";
	public static final String TEMPLATE_CANDIDATE_DETAILS = ProjectMapping.CANDIDATE.BASE_MAPPING+"/details";
	public static final String TEMPLATE_CANDIDATE_ADMIN = ProjectMapping.CANDIDATE.BASE_MAPPING + "/" + ProjectMapping.ADMIN;
	public static final String TEMPLATE_CANDIDATE_LIST = ProjectMapping.CANDIDATE.BASE_MAPPING + "/" + ProjectMapping.LIST;
	public static final String TEMPLATE_CANDIDATE_ADD =  ProjectMapping.CANDIDATE.BASE_MAPPING + "/" + ProjectMapping.ADD;
	public static final String TEMPLATE_CANDIDATE_EDIT = ProjectMapping.CANDIDATE.BASE_MAPPING + "/" + ProjectMapping.EDIT_PAGE;
	public static final String REDIRECT_CANDIDATE_ADMIN_MAPPING = "redirect:/" + ProjectMapping.CANDIDATE.BASE_MAPPING+"/" + ProjectMapping.ADMIN;
	public static final String TEMPLATE_CANDIDATE_REPORT = ProjectMapping.CANDIDATE.BASE_MAPPING + "/" + ProjectMapping.REPORT;;

	@Autowired
	private CursilhistaService cursilhistaService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private RetreatService retreatService;
	
	@Autowired
	private AppConfig appConfig;
	
	private User user;
	
	public List<Retreat> allRetreats;
	
	@Autowired
	CandidateController(CursilhistaService service) {
        super(service, CANDIDATE_ATTRIBUTE_KEY);
        this.cursilhistaService = service;
    }

	/**
	 * method to register a converter to the enum type
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(AddressType.class, new EnumConverter<>(AddressType.class));
	}	
		
	@Secured({PermissionList.CANDIDATE.ADD})
	@RequestMapping(ProjectMapping.ADD)
	String newCandidate(Model model) throws ControllerRestException {
		
		Cursilhista candidate = new Cursilhista();
		
		candidate.setActive( true );
		candidate.setDateCreate( new Date() );
		candidate.setDateLastUpdate( new Date() );
		candidate.setHealthProblem(null);
		candidate.setActiveChurch(null);
		candidate.setPresenterParticipateCursilho(null);
		candidate.setDiet(null);
		candidate.setDrugs(null);
		candidate.setReligion(null);
		
		List<Address> addressList = new ArrayList<>(2);
		addressList.add(new Address(AddressType.S));
		addressList.add(new Address(AddressType.E));
		candidate.setAddress(addressList);
		
		model.addAttribute(RETREAT_ATTRIBUTE_KEY, this.getAllRetreats());
		model.addAttribute(CANDIDATE_ATTRIBUTE_KEY, candidate);
		
		return TEMPLATE_CANDIDATE_ADD;
	}
	
	private void preSave(@ModelAttribute @Validated({Default.class, FieldMatchGroup.class}) Cursilhista candidate, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
		
		if ( candidate != null ) {
			List<Address> address = candidate.getAddress();
			if ( address != null && address.size() > 1 ) {
				
				Address address1 = candidate.getAddress().get(0);
				
				if ( address.get(1).getCompanyAddress() == null || address.get(1).getCompanyAddress().equals("") ) {
					
					candidate.setAddress( null );
					
					address = new ArrayList<>();
					address.add( address1 );
					candidate.setAddress( address );
				}
			}
			
			if ( candidate.getReligion() == null || candidate.getReligion().equals("") ) {
				candidate.setReligion("Catolicismo Romano");
			}
			
			if ( candidate.getNumberCursilhista() == null ) {
				candidate.setNumberCursilhista( 0L );
			}
		}
	}
	
	@Override
	@Secured({PermissionList.CANDIDATE.ADD})
	@RequestMapping(value = ProjectMapping.SAVE, method = RequestMethod.POST)
	String save(@ModelAttribute @Validated({Default.class, FieldMatchGroup.class}) Cursilhista entity, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
		
		this.preSave(entity, result, model, redirectAttributes);
		
		if (result.hasErrors()) {
			model.addAttribute(RETREAT_ATTRIBUTE_KEY, this.getAllRetreats());
			model.addAttribute(CANDIDATE_ATTRIBUTE_KEY, entity);
			return CANDIDATE_ATTRIBUTE_KEY + "/add";
			
		} else {
			try {
				cursilhistaService.save(entity);
			} catch (ServiceException e) {
				logger.error("Error saving " + CANDIDATE_ATTRIBUTE_KEY + ":" + entity.toString(), e);
				model.addAttribute(CANDIDATE_ATTRIBUTE_KEY, entity);
				model.addAttribute(SystemMessages.MESSAGE_ATTRIBUTE, new Message(e.getMessage(), Message.Type.DANGER));
				return CANDIDATE_ATTRIBUTE_KEY + "/add";
			}
			MessageHelper.addSuccessAttribute(redirectAttributes, SystemMessages.ENTITY_SAVE_SUCCESS);
			return "redirect:/" + CANDIDATE_ATTRIBUTE_KEY + "/" + ProjectMapping.ADMIN;
		}
	}
	
	private List<Retreat> getAllRetreats() {
		
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
			logger.error("Erro ao buscar retiros", se);
		} catch(Exception ex){
			logger.error("Erro ao buscar retiros", ex);
		}
		return allRetreats;
	}
	
	/**
	 * <p>
	 * Shows page with a Candidate to edited with the id used
	 * </p>
	 * @param id
	 * @param model
	 * @return
	 * @throws ControllerRestException
	 */
	@Secured(PermissionList.CANDIDATE.EDIT)
	@RequestMapping(value = ProjectMapping.EDIT, method = RequestMethod.GET)
	String editCandidate(@PathVariable("id") Long id, Model model) throws ControllerRestException {
		
		try {
			
			Cursilhista cursilhista = cursilhistaService.findOne(id);
			
			if (cursilhista != null && cursilhista.getAddress() != null && cursilhista.getAddress().size() == 1) {
				cursilhista.getAddress().add(new Address(AddressType.E));
			}
			
			cursilhista.setDateLastUpdate(new Date());
			
			model.addAttribute(RETREAT_ATTRIBUTE_KEY, this.getAllRetreats());
			model.addAttribute(CANDIDATE_ATTRIBUTE_KEY, cursilhista);
			
		} catch(ServiceException se){
			throw new ControllerRestException(SystemMessages.CURSILHISTA_UPDATE_ERRO, se);
		} catch(Exception ex){
			throw new ControllerRestException(SystemMessages.SYSTEM_ERROR, ex);
		} 
		return TEMPLATE_CANDIDATE_EDIT;
	}
	
	@Secured(PermissionList.CANDIDATE.EDIT)
	@RequestMapping(value = ProjectMapping.UPDATE, method = RequestMethod.POST)
	public String update(@ModelAttribute @Validated({Default.class}) Cursilhista candidate, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
		
		if ( candidate != null && candidate.getNumberCursilhista() == null ) {
			candidate.setNumberCursilhista(0L);
		}
		
		return super.update(candidate, result, model, redirectAttributes);
	}
	
	@Secured(PermissionList.CANDIDATE.DETAILS)
	@RequestMapping(value = ProjectMapping.DETAILS, method = RequestMethod.GET)
	String details(@PathVariable("id") Long id, Model model) throws JsonProcessingException, ControllerRestException {
		
		try {
			Cursilhista cursilhista = cursilhistaService.findOne(id);

			model.addAttribute(RETREAT_ATTRIBUTE_KEY, this.getAllRetreats());
			model.addAttribute(CANDIDATE_ATTRIBUTE_KEY, cursilhista);
		} catch(ServiceException se){
			logger.error(SystemMessages.ENTITY_DETAIL_ERROR, se);
			throw new ControllerRestException(ObjectMapperUtil.convertJsonMessage(SystemMessages.ENTITY_DETAIL_ERROR), se);
		} catch(Exception ex){
			logger.error(SystemMessages.SYSTEM_ERROR, ex);
			throw new ControllerRestException(ObjectMapperUtil.convertJsonMessage(SystemMessages.SYSTEM_ERROR), ex);
		} 

		return  TEMPLATE_CANDIDATE_DETAILS;
	}
	
	@Secured(PermissionList.CANDIDATE.ACTIVE_INACTIVE)
	@RequestMapping(value = ProjectMapping.ACTIVE_INACTIVE, method=RequestMethod.PUT)
	ResponseEntity<String> activate(@RequestParam(value="id") Long id) throws ControllerRestException {
		return super.activate(id);
	}
	
	@Secured(PermissionList.CANDIDATE.DELETE)
	@RequestMapping(value = ProjectMapping.DELETE, method=RequestMethod.DELETE, produces=MediaType.APPLICATION_JSON_VALUE)	
	ResponseEntity<String> delete(@PathVariable(value="id") Long id) throws ControllerRestException {
		return super.delete(id);
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
	@Secured(PermissionList.CANDIDATE.ADMIN)
	@RequestMapping(ProjectMapping.ADMIN)
	String adminList(
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "numberRetreat", required = false) Long numberRetreat,			
			@RequestParam(value = "active", required = false) Boolean active, ModelMap model, Pageable pageable, HttpServletRequest request)
			throws ControllerRestException, ServiceException {

		if ( numberRetreat != null && numberRetreat == -1) numberRetreat = null;
		
		this.populateUserSession( request );
		
		listCandidates(name, numberRetreat, active, model, pageable, true, request);

		return TEMPLATE_CANDIDATE_ADMIN;
	}

	private void populateUserSession( HttpServletRequest request ) {
		
		Long idUser = (Long) request.getSession().getAttribute("user");
		
		user = new User( idUser );
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
	@Secured(PermissionList.CANDIDATE.LIST)
	@RequestMapping(ProjectMapping.LIST)
	String list(
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "numberRetreat", required = false) Long numberRetreat,
			@RequestParam(value = "active", required = false) Boolean active, ModelMap model, Pageable pageable, HttpServletRequest request){

		if ( numberRetreat != null && numberRetreat == -1) numberRetreat = null;
		
		this.populateUserSession( request );
		
		listCandidates(name, numberRetreat, active, model, pageable, false, request);

		return TEMPLATE_CANDIDATE_LIST;
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
	@Secured(PermissionList.CANDIDATE.REPORT)
	@RequestMapping(ProjectMapping.REPORT)
	String report(
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "numberRetreat", required = false) Long numberRetreat,
			@RequestParam(value = "active", required = false) Boolean active, ModelMap model, Pageable pageable, HttpServletRequest request){

		if ( numberRetreat != null && numberRetreat == -1) numberRetreat = null;
		
		listCandidates(name, numberRetreat, active, model, pageable, false, request);

		return TEMPLATE_CANDIDATE_REPORT;
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
	private void listCandidates(String name, Long retreatNumber, Boolean active, ModelMap model,
			Pageable pageable, boolean isAdministrative, HttpServletRequest request) {

		StringBuffer url = new StringBuffer();
		MetaEntity<Cursilhista> metadata	= null;
		Pageable newPageable 			= PageableUtil.getPageRequest(pageable, SystemMessages.ITEMS_PER_PAGE);
		Page<Cursilhista> cPage 		= new PageImpl<>(new ArrayList<Cursilhista>());
		
		try {
			metadata	= new MetaEntity<>(Cursilhista.class, appConfig);

			url = request.getRequestURL().append(request.getQueryString() != null ? "?"+request.getQueryString() : "");
			
			User userLocal = userService.findUserById( user.getId() );
			
			if ( userLocal != null && userLocal.getProfiles() != null ) {
				
				if ( userLocal.getProfiles().size() == 1 
						&& ( userLocal.getProfiles().get(0).getName().equalsIgnoreCase( ProfileType.PROFILE_SECRETARY.toString() ) 
								|| userLocal.getProfiles().get(0).getName().equalsIgnoreCase( ProfileType.PROFILE_COORDINATOR.toString() ) ) ) {
					
					if ( userLocal.getRetreat() != null ) {
						retreatNumber = userLocal.getRetreat().getNumber();
					}
				}
			}
			
			cPage = cursilhistaService.findByNameAndRetreatNumberAndActiveContaining( name, retreatNumber, active, newPageable);
			
			for (Cursilhista c : cPage.getContent()) {
				if ( c.getNumberCursilhista() == null ) {
					c.setNumberCursilhista(0L);
				}
			}
			
			PageWrapper<Cursilhista> page = new PageWrapper<>(cPage, url.toString(), metadata, pageable.getSort());

			model.addAttribute("page", page);
			
			model.addAttribute(RETREAT_ATTRIBUTE_KEY, this.getAllRetreats());
			
		} catch (ServiceException | MetadataException me) {
			logger.error(ERROR_OBTAINING_DATA, me);
			createReturnExceptionList(model, pageable, url.toString(), metadata, cPage, me.getMessage());
		} catch (Exception ex) {
			logger.error(ERROR_OBTAINING_DATA, ex);
			createReturnExceptionList(model, pageable, url.toString(), metadata, cPage, SystemMessages.SYSTEM_ERROR);
		}
	}
	
	private void createReturnExceptionList(ModelMap model, Pageable pageable, String url,
			MetaEntity<Cursilhista> metadata, Page<Cursilhista> cPage, String message) {
		
		model.addAttribute(SystemMessages.MESSAGE_ATTRIBUTE, new Message(message, Message.Type.DANGER));
		PageWrapper<Cursilhista> page = new PageWrapper<>(cPage, url, metadata, pageable.getSort());
		model.addAttribute("page", page);
	}
	
}
