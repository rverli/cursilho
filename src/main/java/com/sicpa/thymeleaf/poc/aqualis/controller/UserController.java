package com.sicpa.thymeleaf.poc.aqualis.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.groups.Default;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sicpa.thymeleaf.poc.aqualis.enumerator.ProfileType;
import com.sicpa.thymeleaf.poc.aqualis.environment.config.AppConfig;
import com.sicpa.thymeleaf.poc.aqualis.exception.ControllerPageException;
import com.sicpa.thymeleaf.poc.aqualis.exception.ControllerRestException;
import com.sicpa.thymeleaf.poc.aqualis.exception.MetadataException;
import com.sicpa.thymeleaf.poc.aqualis.exception.ServiceException;
import com.sicpa.thymeleaf.poc.aqualis.messages.Message;
import com.sicpa.thymeleaf.poc.aqualis.messages.Message.Type;
import com.sicpa.thymeleaf.poc.aqualis.messages.MessageHelper;
import com.sicpa.thymeleaf.poc.aqualis.messages.ObjectMapperUtil;
import com.sicpa.thymeleaf.poc.aqualis.messages.SystemMessages;
import com.sicpa.thymeleaf.poc.aqualis.metadata.MetaEntity;
import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.Profile;
import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.Retreat;
import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.User;
import com.sicpa.thymeleaf.poc.aqualis.security.PermissionList;
import com.sicpa.thymeleaf.poc.aqualis.service.ProfileService;
import com.sicpa.thymeleaf.poc.aqualis.service.RetreatService;
import com.sicpa.thymeleaf.poc.aqualis.service.UserService;
import com.sicpa.thymeleaf.poc.aqualis.utils.PageWrapper;
import com.sicpa.thymeleaf.poc.aqualis.utils.PageableUtil;
import com.sicpa.thymeleaf.poc.aqualis.utils.ProjectMapping;
import com.sicpa.thymeleaf.poc.aqualis.utils.StringUtils;
import com.sicpa.thymeleaf.poc.aqualis.utils.form.validators.fieldmatch.FieldMatchGroup;

/**
 * Controller for actions on {@link User}
 *
 */
@Controller
@RequestMapping(ProjectMapping.USER.BASE_MAPPING)
public class UserController extends AbstractCrudController<User, Long>{
	
	private static final String PROFILE_ATTRIBUTE_KEY = "profiles";
	private static final String USER_ATTRIBUTE_KEY = "user";
	private static final String RETREAT_ATTRIBUTE_KEY = "retreats";

	private static final Logger logger = Logger.getLogger(UserController.class);

	static final String TEMPLATE_USER_REPORT 	= ProjectMapping.USER.BASE_MAPPING + "/" + ProjectMapping.REPORT;

	static final String TEMPLATE_USER_LIST 		= ProjectMapping.USER.BASE_MAPPING +"/list";
	
	static final String TEMPLATE_USER_DETAILS 	= ProjectMapping.USER.BASE_MAPPING+"/details";
	
	static final String TEMPLATE_USER_ADMIN 	= ProjectMapping.USER.BASE_MAPPING+"/admin";

	static final String TEMPLATE_USER_ADD 		= ProjectMapping.USER.BASE_MAPPING+"/add";

	static final String TEMPLATE_USER_EDIT 		= ProjectMapping.USER.BASE_MAPPING+"/edit";

	static final String REDIRECT_USER_ADMIN_MAPPING = "redirect:/" + TEMPLATE_USER_ADMIN;
	
	private List<Retreat> allRetreats = null;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AppConfig appConfig;
	
	@Autowired
	private ProfileService profileService;
	
	@Autowired
	private RetreatService retreatService;	
	
	/**
	 * 
	 * @param service Service injected by constructor
	 */
	@Autowired
	public UserController(UserService service) {
        super(service, USER_ATTRIBUTE_KEY);
        this.userService = service;
    }

	@Secured(PermissionList.USER.LIST)
	@RequestMapping(value = ProjectMapping.LIST)
	String list(@RequestParam(value="name", required=false) String name,
			@RequestParam(value="email", required=false) String email,
			@RequestParam(value="active", required=false) Boolean active,
			ModelMap model, Pageable pageable, HttpServletRequest request)  {

		try {
			
			listIntern(name, email, active, model, pageable, request);
			
		} catch(ServiceException se){
			logger.error("Error listando users", se);
			model.addAttribute(SystemMessages.MESSAGE_ATTRIBUTE, new Message(se.getMessage(), Type.DANGER));
		} 
			
		return TEMPLATE_USER_LIST;
	}


	@Secured(PermissionList.USER.ADMIN)
	@RequestMapping(value = ProjectMapping.ADMIN)
	String adminList(@RequestParam(value="name", required=false) String name,
			@RequestParam(value="email", required=false) String email,
			@RequestParam(value="active", required=false) Boolean active,
			ModelMap model, Pageable pageable, HttpServletRequest request) throws ControllerRestException  {
		
		try{
			
			listIntern(name, email, active, model, pageable, request);
		
		} catch(ServiceException se){
			logger.error("Error listando users", se);
			model.addAttribute(SystemMessages.MESSAGE_ATTRIBUTE, new Message(se.getMessage(), Type.DANGER));
		} 
		
		return TEMPLATE_USER_ADMIN;
	}
	
	@Override
	@Secured(PermissionList.USER.DETAILS)
	@RequestMapping(value = ProjectMapping.DETAILS, method = RequestMethod.GET)
	String details(@PathVariable("id") Long id, Model model) throws JsonProcessingException, ControllerRestException {
		
		try {
			loadUserObjectToView(id, model);
			loadProfileListToView(model);
			loadRetreatListToView(model);
			
		} catch(ServiceException se){
			logger.error(SystemMessages.ENTITY_DETAIL_ERROR, se);
			throw new ControllerRestException(ObjectMapperUtil.convertJsonMessage(SystemMessages.ENTITY_DETAIL_ERROR), se);
		} catch(Exception ex){
			logger.error(SystemMessages.SYSTEM_ERROR, ex);
			throw new ControllerRestException(ObjectMapperUtil.convertJsonMessage(SystemMessages.SYSTEM_ERROR), ex);
		} 

		return  TEMPLATE_USER_DETAILS;
	}
	
	private void loadUserObjectToView(Long id, Model model) throws ServiceException {
		User user = userService.findOne(id);
		model.addAttribute(USER_ATTRIBUTE_KEY, user);
	}

	private void loadRetreatListToView(Model model) throws ServiceException {
		model.addAttribute(RETREAT_ATTRIBUTE_KEY, this.getAllRetreats());
	}
	
	private void loadProfileListToView(Model model) throws ServiceException {
		
		List<Profile> profiles = profileService.findAll();
		
		List<Profile> profilesFinal = new ArrayList<>();
		
		if ( profiles != null ) {
			
			for (Profile profile : profiles) {
				
				if ( !profile.getName().equals(ProfileType.PROFILE_ADMIN.toString()) ) {
					
					profilesFinal.add(profile);
				}
			}
		}
		
		model.addAttribute(PROFILE_ATTRIBUTE_KEY, profilesFinal);
	}
	
	private List<Retreat> getAllRetreats() throws ServiceException {
		
		try {
			allRetreats = retreatService.findAllRetreats();
		} catch(ServiceException se){
			throw new ServiceException(SystemMessages.USER_UPDATE_ERRO, se);
		} catch(Exception ex){
			throw new ServiceException(SystemMessages.SYSTEM_ERROR, ex);
		}
		return allRetreats;
	}
	
	@Secured(PermissionList.USER.ADMIN)
	@RequestMapping(ProjectMapping.ADD)
	String newUser(Model model, RedirectAttributes redirectAttributes) throws ControllerPageException {
		
		
		try {
			User user = new User();
			user.setActive(true);
			
			model.addAttribute(USER_ATTRIBUTE_KEY, user);
			
			loadRetreatListToView(model);
			loadProfileListToView(model);
			
		} catch (Exception e) {
			logger.error("Erro ao criar novo usuario", e);
			MessageHelper.addErrorAttribute(redirectAttributes, SystemMessages.SYSTEM_ERROR);
			return REDIRECT_USER_ADMIN_MAPPING;
		}
		return TEMPLATE_USER_ADD;
	}
	
	@RequestMapping("/feedback")
	String newUser(RedirectAttributes redirectAttributes) {

		MessageHelper.addSuccessAttribute(redirectAttributes, SystemMessages.SYSTEM_ERROR);
		
		return REDIRECT_USER_ADMIN_MAPPING;
	}
	
	/**
	 * <p>
	 * Shows page with a user to edited with the id used
	 * </p>
	 * @param id
	 * @param model
	 * @return
	 * @throws ControllerRestException
	 */
	@Secured(PermissionList.USER.ADMIN)
	@RequestMapping(value = ProjectMapping.EDIT, method = RequestMethod.GET)
	String editUser(@PathVariable("id") Long id, Model model) throws ControllerRestException {
		
		try {			
			loadUserObjectToView(id, model);
			loadProfileListToView(model);
			loadRetreatListToView(model);
			
		} catch(ServiceException se){
			throw new ControllerRestException(SystemMessages.USER_UPDATE_ERRO, se);
		} catch(Exception ex){
			throw new ControllerRestException(SystemMessages.SYSTEM_ERROR, ex);
		}
		
		return TEMPLATE_USER_EDIT;
	}
	
	private String validateFields( User user, Model model ) {
		
		if ( user == null ) {
			return null;
		}
		
		if ( user.getProfiles() == null 
				|| user.getProfiles().size() == 0 
				|| user.getProfiles().get( 0 ) == null ) {
			return this.sendMessage(model, "O campo 'Perfil' deve ser preenchido.");
		}
		
		if ( user.getRetreat() == null ) {
			return this.sendMessage(model, "O campo 'Número do Retiro' deve ser preenchido.");
		}
		
		return null;
	}
	
	private String sendMessage( Model model, String message ) {
		
		model.addAttribute( SystemMessages.MESSAGE_ATTRIBUTE, new Message(message, Type.WARNING) );
		
		this.reloadCombos( model );
		
		return TEMPLATE_USER_ADD;
	}
	
	/**
	 * Performs list users to both interfaces list and admin list.
	 *  
	 * @param uri base URL
	 * @param name <code>name</code> parameter value
	 * @param email <code>email</code> parameter value
	 * @param active <code>active</code> parameter value
	 * @param model page model	
	 * @param pageable {@link PageRequest} to perform pagination list
	 * @throws ServiceException 
	 * @throws Exception if any error occurs
	 */
	private void listIntern(String name, String email, Boolean active, ModelMap model, Pageable pageable, HttpServletRequest request) throws ServiceException {
		
		PageWrapper<User> page		= null;
		MetaEntity<User> metadata 	= null;
		Pageable newPageable 		= PageableUtil.getPageRequest(pageable, SystemMessages.ITEMS_PER_PAGE);
		Page<User> userPage			= new PageImpl<>(new ArrayList<User>());
		StringBuilder url = new StringBuilder();
		
		try{
			metadata = new MetaEntity<>(User.class, appConfig);

			url.append(request.getRequestURL().append(request.getQueryString() != null ? "?"+request.getQueryString() : ""));
			
			userPage = userService.findByNameAndEmailAndActiveContaining(name, email, active, newPageable);
			page = new PageWrapper<>(userPage, url.toString() , metadata, pageable.getSort());

		} catch(MetadataException me){
			throw new ServiceException(SystemMessages.USER_UPDATE_ERRO, me);
		} catch(ServiceException se){
			throw new ServiceException(SystemMessages.USER_UPDATE_ERRO, se);
		} catch(Exception ex){
			throw new ServiceException(SystemMessages.SYSTEM_ERROR, ex);
		}finally {
			if( page == null ){
				page = new PageWrapper<>(userPage, url.toString(), metadata, pageable.getSort());
			}
			model.addAttribute("page", page);
		}
	}

	/**
	 * Generate the report
	 *  
	 * @param uri base URL
	 * @param name <code>name</code> parameter value
	 * @param email <code>email</code> parameter value
	 * @param active <code>active</code> parameter value
	 * @param model page model	
	 * @param pageable {@link PageRequest} to perform pagination list
	 * @throws ServiceException 
	 * @throws Exception if any error occurs
	 */
	private void listReport(String name, String email, Boolean active, ModelMap model, Pageable pageable) throws ServiceException {
		
		PageWrapper<User> page		= null;
		MetaEntity<User> metadata 	= null;
		Pageable newPageable 		= PageableUtil.getPageRequest(pageable, SystemMessages.ITEMS_PER_PAGE);
		Page<User> userPage			= new PageImpl<>(new ArrayList<User>());
		StringBuilder url 			= new StringBuilder();
		
		try{
			metadata 	= new MetaEntity<>(User.class, appConfig);

			url.append("/")
			.append(ProjectMapping.USER.BASE_MAPPING).append("/").append(ProjectMapping.REPORT)
			.append("?")
			.append("name=")
			.append(StringUtils.encodeStringUTF8(name))
			.append("&email=")
			.append(StringUtils.encodeStringUTF8(email))
			.append("&active=")
			.append(active!=null ? StringUtils.encodeStringUTF8(active ? "1" : "0") : "");
			
			userPage		= userService.findByNameAndEmailAndActiveContaining(name, email, active, newPageable);
			page 			= new PageWrapper<>(userPage, url.toString() , metadata, pageable.getSort());

			
		} catch(MetadataException | ServiceException  me){
			throw new ServiceException(SystemMessages.USER_UPDATE_ERRO, me);
		} catch(Exception ex){
			throw new ServiceException(SystemMessages.SYSTEM_ERROR, ex);
		}finally {
			if( page == null ){
				page = new PageWrapper<>(userPage, url.toString(), metadata, pageable.getSort());
			}
			model.addAttribute("page", page);
		}
	}
	
	@Secured(ProjectMapping.PROFILE.USER)
	@RequestMapping(value = ProjectMapping.REPORT)
	String report(@RequestParam(value="name", required=false) String name,
			@RequestParam(value="email", required=false) String email,
			@RequestParam(value="active", required=false) Boolean active,
			ModelMap model, Pageable pageable)  {

		try {
			
			listReport(name, email, active, model, pageable);
			
		} catch(ServiceException se){
			logger.error("Error listando users", se);
			model.addAttribute(SystemMessages.MESSAGE_ATTRIBUTE, new Message(se.getMessage(), Type.DANGER));
		} 
			
		return TEMPLATE_USER_REPORT;
	}

	/* (non-Javadoc)
	 * @see com.sicpa.thymeleaf.poc.aqualis.controller.AbstractCrudController#update(java.lang.Object, org.springframework.validation.BindingResult, org.springframework.ui.Model, org.springframework.web.servlet.mvc.support.RedirectAttributes)
	 */
	@Secured(PermissionList.USER.ADMIN)
	@RequestMapping(value = ProjectMapping.SAVE, method = RequestMethod.POST)
	String save(@ModelAttribute @Validated({Default.class, FieldMatchGroup.class}) User user, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
		
		String validate = this.validateFields( user, model );
		
		if ( validate != null ) {
			return validate;
		}
		
		return super.save(user, result, model, redirectAttributes);
	}
	
	private void reloadCombos( Model model ) {
		try {
			loadRetreatListToView(model);
			loadProfileListToView(model);
		} catch (ServiceException e) {
			logger.error("Erro ao recarregar combos.");
		}
	}
	
	/* (non-Javadoc)
	 * @see com.sicpa.thymeleaf.poc.aqualis.controller.AbstractCrudController#update(java.lang.Object, org.springframework.validation.BindingResult, org.springframework.ui.Model, org.springframework.web.servlet.mvc.support.RedirectAttributes)
	 */
	@Secured(PermissionList.USER.ADMIN)
	@RequestMapping(value = ProjectMapping.UPDATE, method = RequestMethod.POST)
	public String update(@ModelAttribute @Validated({Default.class}) User user, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
		
		String validate = this.validateFields( user, model );
		
		if ( validate != null ) {
			return validate;
		}
		
		return super.update(user, result, model, redirectAttributes);
	}
	
	/* (non-Javadoc)
	 * @see com.sicpa.thymeleaf.poc.aqualis.controller.AbstractCrudController#activate(java.io.Serializable)
	 */
	@Secured(PermissionList.USER.ADMIN)
	@RequestMapping(value = ProjectMapping.ACTIVE_INACTIVE, method=RequestMethod.PUT)
	ResponseEntity<String> activate(@RequestParam(value="id") Long id) throws ControllerRestException {
		return super.activate(id);
	}
	
	/* (non-Javadoc)
	 * @see com.sicpa.thymeleaf.poc.aqualis.controller.AbstractCrudController#delete(java.io.Serializable)
	 */
	@Secured(PermissionList.USER.ADMIN)
	@RequestMapping(value = ProjectMapping.DELETE, method=RequestMethod.DELETE, produces=MediaType.APPLICATION_JSON_VALUE)	
	ResponseEntity<String> delete(@PathVariable(value="id") Long id) throws ControllerRestException {
		return super.delete(id);
	}
	
	public void setProfileService(ProfileService profileService) {
		this.profileService = profileService;
	}
	
}
