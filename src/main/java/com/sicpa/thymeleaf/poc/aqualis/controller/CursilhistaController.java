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
import com.sicpa.thymeleaf.poc.aqualis.security.PermissionList;
import com.sicpa.thymeleaf.poc.aqualis.service.CursilhistaService;
import com.sicpa.thymeleaf.poc.aqualis.service.RetreatService;
import com.sicpa.thymeleaf.poc.aqualis.utils.PageWrapper;
import com.sicpa.thymeleaf.poc.aqualis.utils.PageableUtil;
import com.sicpa.thymeleaf.poc.aqualis.utils.ProjectMapping;
import com.sicpa.thymeleaf.poc.aqualis.utils.form.converters.EnumConverter;
import com.sicpa.thymeleaf.poc.aqualis.utils.form.validators.fieldmatch.FieldMatchGroup;

@Controller
@RequestMapping(ProjectMapping.CURSILHISTA.BASE_MAPPING)
public class CursilhistaController extends AbstractCrudController<Cursilhista, Long>{

	private static final Logger logger = Logger.getLogger(CursilhistaController.class);
	
	private static final String ERROR_OBTAINING_DATA = "Error obtaining data";
	private static final String CURSILHISTA_ATTRIBUTE_KEY = "cursilhista";
	private static final String RETREAT_ATTRIBUTE_KEY = "retreats";
	public static final String TEMPLATE_CURSILHISTA_DETAILS = ProjectMapping.CURSILHISTA.BASE_MAPPING+"/details";
	public static final String TEMPLATE_CURSILHISTA_ADMIN = ProjectMapping.CURSILHISTA.BASE_MAPPING + "/" + ProjectMapping.ADMIN;
	public static final String TEMPLATE_CURSILHISTA_LIST = ProjectMapping.CURSILHISTA.BASE_MAPPING + "/" + ProjectMapping.LIST;
	public static final String TEMPLATE_CURSILHISTA_ADD =  ProjectMapping.CURSILHISTA.BASE_MAPPING + "/" + ProjectMapping.ADD;
	public static final String TEMPLATE_CURSILHISTA_EDIT = ProjectMapping.CURSILHISTA.BASE_MAPPING + "/" + ProjectMapping.EDIT_PAGE;
	public static final String REDIRECT_CURSILHISTA_ADMIN_MAPPING = "redirect:/" + ProjectMapping.CURSILHISTA.BASE_MAPPING+"/" + ProjectMapping.ADMIN;
	public static final String TEMPLATE_CURSILHISTA_REPORT = ProjectMapping.CURSILHISTA.BASE_MAPPING + "/" + ProjectMapping.REPORT;
	
	public static final String REDIRECT_CURSILHISTA_ADD_MAPPING = "redirect:/" + ProjectMapping.CURSILHISTA.BASE_MAPPING+"/" + ProjectMapping.ADD;

	@Autowired
	private CursilhistaService cursilhistaService;

	@Autowired
	private RetreatService retreatService;
	
	@Autowired
	private AppConfig appConfig;
	
	public List<Retreat> allRetreats;
	
	@Autowired
	CursilhistaController(CursilhistaService service) {
        super(service, CURSILHISTA_ATTRIBUTE_KEY);
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
		
	@Secured({PermissionList.CURSILHISTA.ADD})
	@RequestMapping(ProjectMapping.ADD)
	String newCursilhista(Model model) throws ControllerRestException {
		
		Cursilhista cursilhista = new Cursilhista();
		
		cursilhista.setActive( true );
		cursilhista.setDateCreate( new Date() );
		cursilhista.setDateLastUpdate( new Date() );
		cursilhista.setMaritalStatus(null);
		
		List<Address> addressList = new ArrayList<>(2);
		addressList.add(new Address(AddressType.S));
		addressList.add(new Address(AddressType.E));
		cursilhista.setAddress(addressList);
		
		model.addAttribute(RETREAT_ATTRIBUTE_KEY, this.getAllRetreats());
		model.addAttribute(CURSILHISTA_ATTRIBUTE_KEY, cursilhista);
		
		return TEMPLATE_CURSILHISTA_ADD;
	}
	
	private void preSave(@ModelAttribute @Validated({Default.class, FieldMatchGroup.class}) Cursilhista cursilhista, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
		
		if ( cursilhista != null ) {
			List<Address> address = cursilhista.getAddress();
			if ( address != null && address.size() > 1 ) {
				
				Address address1 = cursilhista.getAddress().get(0);
				
				if ( address.get(1).getCompanyAddress() == null || address.get(1).getCompanyAddress().equals("") ) {
					
					cursilhista.setAddress( null );
					
					address = new ArrayList<>();
					address.add( address1 );
					cursilhista.setAddress( address );
				}
			}
			
			this.validateNotNullFields( cursilhista );
		}
	}
	
	private void validateNotNullFields( Cursilhista cursilhista ) {
		
		if ( cursilhista != null ) {
		
			if ( cursilhista.getReligion() == null || cursilhista.getReligion().equals("") ) {
				cursilhista.setReligion("Catolicismo Romano");
			}
		}
	}
	
	@Override
	@Secured({PermissionList.CURSILHISTA.ADD})
	@RequestMapping(value = ProjectMapping.SAVE, method = RequestMethod.POST)
	String save(@ModelAttribute @Validated({Default.class, FieldMatchGroup.class}) Cursilhista entity, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
		
		this.preSave(entity, result, model, redirectAttributes);
		
		if (result.hasErrors()) {
			model.addAttribute(RETREAT_ATTRIBUTE_KEY, this.getAllRetreats());
			model.addAttribute(CURSILHISTA_ATTRIBUTE_KEY, entity);
			return CURSILHISTA_ATTRIBUTE_KEY + "/add";
			
		} else {
			try {
				cursilhistaService.save(entity);
			} catch (ServiceException e) {
				logger.error("Error saving " + CURSILHISTA_ATTRIBUTE_KEY + ":" + entity.toString(), e);
				model.addAttribute(CURSILHISTA_ATTRIBUTE_KEY, entity);
				model.addAttribute(SystemMessages.MESSAGE_ATTRIBUTE, new Message(e.getMessage(), Message.Type.DANGER));
				return CURSILHISTA_ATTRIBUTE_KEY + "/add";
			}
			MessageHelper.addSuccessAttribute(redirectAttributes, SystemMessages.ENTITY_SAVE_SUCCESS);
			return "redirect:/" + CURSILHISTA_ATTRIBUTE_KEY + "/" + ProjectMapping.ADMIN;
		}
	}
	
	private List<Retreat> getAllRetreats() {
		
		try {
			allRetreats = retreatService.findAllRetreats();
		} catch(ServiceException se){
			logger.error("Erro ao buscar retiros", se);
			//throw new ControllerRestException(SystemMessages.USER_UPDATE_ERRO, se);
		} catch(Exception ex){
			logger.error("Erro ao buscar retiros", ex);
			//throw new ControllerRestException(SystemMessages.SYSTEM_ERROR, ex);
		}
		return allRetreats;
	}
	
	/**
	 * <p>
	 * Shows page with a cursilhista to edited with the id used
	 * </p>
	 * @param id
	 * @param model
	 * @return
	 * @throws ControllerRestException
	 */
	@Secured(PermissionList.CURSILHISTA.EDIT)
	@RequestMapping(value = ProjectMapping.EDIT, method = RequestMethod.GET)
	String editCursilhista(@PathVariable("id") Long id, Model model) throws ControllerRestException {
		
		try {
			
			Cursilhista cursilhista = cursilhistaService.findOne(id);
			
			if (cursilhista != null && cursilhista.getAddress() != null && cursilhista.getAddress().size() == 1) {
				cursilhista.getAddress().add(new Address(AddressType.E));
			}
			
			cursilhista.setDateLastUpdate(new Date());
			
			model.addAttribute(RETREAT_ATTRIBUTE_KEY, this.getAllRetreats());
			model.addAttribute(CURSILHISTA_ATTRIBUTE_KEY, cursilhista);
			
		} catch(ServiceException se){
			throw new ControllerRestException(SystemMessages.CURSILHISTA_UPDATE_ERRO, se);
		} catch(Exception ex){
			throw new ControllerRestException(SystemMessages.SYSTEM_ERROR, ex);
		} 
		return TEMPLATE_CURSILHISTA_EDIT;
	}
	
	@Secured(PermissionList.CURSILHISTA.EDIT)
	@RequestMapping(value = ProjectMapping.UPDATE, method = RequestMethod.POST)
	public String update(@ModelAttribute @Validated({Default.class}) Cursilhista company, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
		return super.update(company, result, model, redirectAttributes);
	}
	
	@Secured(PermissionList.CURSILHISTA.DETAILS)
	@RequestMapping(value = ProjectMapping.DETAILS, method = RequestMethod.GET)
	String details(@PathVariable("id") Long id, Model model) throws JsonProcessingException, ControllerRestException {
		
		try {
			Cursilhista cursilhista = cursilhistaService.findOne(id);

			model.addAttribute(RETREAT_ATTRIBUTE_KEY, this.getAllRetreats());
			model.addAttribute(CURSILHISTA_ATTRIBUTE_KEY, cursilhista);
		} catch(ServiceException se){
			logger.error(SystemMessages.ENTITY_DETAIL_ERROR, se);
			throw new ControllerRestException(ObjectMapperUtil.convertJsonMessage(SystemMessages.ENTITY_DETAIL_ERROR), se);
		} catch(Exception ex){
			logger.error(SystemMessages.SYSTEM_ERROR, ex);
			throw new ControllerRestException(ObjectMapperUtil.convertJsonMessage(SystemMessages.SYSTEM_ERROR), ex);
		} 

		return  TEMPLATE_CURSILHISTA_DETAILS;
	}
	
	@Secured(PermissionList.CURSILHISTA.ACTIVE_INACTIVE)
	@RequestMapping(value = ProjectMapping.ACTIVE_INACTIVE, method=RequestMethod.PUT)
	ResponseEntity<String> activate(@RequestParam(value="id") Long id) throws ControllerRestException {
		return super.activate(id);
	}
	
	@Secured(PermissionList.CURSILHISTA.DELETE)
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
	@Secured(PermissionList.CURSILHISTA.ADMIN)
	@RequestMapping(ProjectMapping.ADMIN)
	String adminList(
			@RequestParam(value = "numberCursilhista", required = false) String numberCursilhista,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "numberRetreat", required = false) Long numberRetreat,			
			@RequestParam(value = "active", required = false) Boolean active, ModelMap model, Pageable pageable, HttpServletRequest request)
			throws ControllerRestException, ServiceException {

		if ( numberRetreat != null && numberRetreat == -1) numberRetreat = null;
		
		/* TODO Validar campo somente números*
		if ( numberCursilhista != null && !numberCursilhista.matches("[0-9]+") ) {
			model.addAttribute("O campo 'Número do Cursilhista' deve conter somente números.", new Message("O campo 'Número do Cursilhista' deve conter somente números.", Type.DANGER));
			//throw new ControllerRestException("O campo 'Número do Cursilhista' deve conter somente números.");
			return TEMPLATE_CURSILHISTA_ADMIN;
		}
		*/
		listCursilhistas(numberCursilhista, name, numberRetreat, active, model, pageable, true, request);

		return TEMPLATE_CURSILHISTA_ADMIN;
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
	@Secured(PermissionList.CURSILHISTA.LIST)
	@RequestMapping(ProjectMapping.LIST)
	String list(
			@RequestParam(value = "numberCursilhista", required = false) String numberCursilhista,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "numberRetreat", required = false) Long numberRetreat,
			@RequestParam(value = "active", required = false) Boolean active, ModelMap model, Pageable pageable, HttpServletRequest request){

		if ( numberRetreat != null && numberRetreat == -1) numberRetreat = null;
		
		listCursilhistas(numberCursilhista, name, numberRetreat, active, model, pageable, false, request);

		return TEMPLATE_CURSILHISTA_LIST;
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
	@Secured(PermissionList.CURSILHISTA.REPORT)
	@RequestMapping(ProjectMapping.REPORT)
	String report(
			@RequestParam(value = "numberCursilhista", required = false) String numberCursilhista,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "numberRetreat", required = false) Long numberRetreat,
			@RequestParam(value = "active", required = false) Boolean active, ModelMap model, Pageable pageable, HttpServletRequest request){

		if ( numberRetreat != null && numberRetreat == -1) numberRetreat = null;
		
		listCursilhistas(numberCursilhista, name, numberRetreat, active, model, pageable, false, request);

		return TEMPLATE_CURSILHISTA_REPORT;
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
	private void listCursilhistas(String numberCursilhista, String name, Long retreatNumber, Boolean active, ModelMap model,
			Pageable pageable, boolean isAdministrative, HttpServletRequest request) {

		StringBuffer url = new StringBuffer();
		MetaEntity<Cursilhista> metadata	= null;
		Pageable newPageable 			= PageableUtil.getPageRequest(pageable, SystemMessages.ITEMS_PER_PAGE);
		Page<Cursilhista> cPage 		= new PageImpl<>(new ArrayList<Cursilhista>());
		try {
			metadata	= new MetaEntity<>(Cursilhista.class, appConfig);

			url = request.getRequestURL().append(request.getQueryString() != null ? "?"+request.getQueryString() : "");

			cPage = cursilhistaService.findByNumberCursilhistaAndNameAndRetreatNumberAndActiveContaining(numberCursilhista, name, retreatNumber, active, newPageable);

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
