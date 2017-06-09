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
import com.sicpa.thymeleaf.poc.aqualis.environment.config.AppConfig;
import com.sicpa.thymeleaf.poc.aqualis.exception.ControllerRestException;
import com.sicpa.thymeleaf.poc.aqualis.exception.MetadataException;
import com.sicpa.thymeleaf.poc.aqualis.exception.ServiceException;
import com.sicpa.thymeleaf.poc.aqualis.messages.Message;
import com.sicpa.thymeleaf.poc.aqualis.messages.SystemMessages;
import com.sicpa.thymeleaf.poc.aqualis.metadata.MetaEntity;
import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.Address;
import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.RetreatHouse;
import com.sicpa.thymeleaf.poc.aqualis.security.PermissionList;
import com.sicpa.thymeleaf.poc.aqualis.service.RetreatHouseService;
import com.sicpa.thymeleaf.poc.aqualis.utils.PageWrapper;
import com.sicpa.thymeleaf.poc.aqualis.utils.PageableUtil;
import com.sicpa.thymeleaf.poc.aqualis.utils.ProjectMapping;
import com.sicpa.thymeleaf.poc.aqualis.utils.form.converters.EnumConverter;
import com.sicpa.thymeleaf.poc.aqualis.utils.form.validators.fieldmatch.FieldMatchGroup;

/**
 * <p>
 * Controller for operation on the {@link RetreatHouse} entity like list, update add
 * and delete
 * </p>
 * 
 */
@Controller
@RequestMapping(ProjectMapping.RETREAT_HOUSE.BASE_MAPPING)
public class RetreatHouseController extends AbstractCrudController<RetreatHouse, Long>{

	private static final Logger logger = Logger.getLogger(RetreatHouseController.class);
	
	private static final String ERROR_OBTAINING_DATA = "Error obtaining data";
	private static final String RETREAT_HOUSE_ATTRIBUTE_KEY = "retreathouse";	
	public static final String TEMPLATE_RETREAT_HOUSE_DETAILS = ProjectMapping.RETREAT_HOUSE.BASE_MAPPING+"/details";	
	public static final String TEMPLATE_RETREAT_HOUSE_ADMIN = ProjectMapping.RETREAT_HOUSE.BASE_MAPPING + "/" + ProjectMapping.ADMIN;
	public static final String TEMPLATE_RETREAT_HOUSE_LIST = ProjectMapping.RETREAT_HOUSE.BASE_MAPPING + "/" + ProjectMapping.LIST;
	public static final String TEMPLATE_RETREAT_HOUSE_ADD =  ProjectMapping.RETREAT_HOUSE.BASE_MAPPING + "/" + ProjectMapping.ADD;	
	public static final String TEMPLATE_RETREAT_HOUSE_EDIT = ProjectMapping.RETREAT_HOUSE.BASE_MAPPING + "/" + ProjectMapping.EDIT_PAGE;
	public static final String REDIRECT_RETREAT_HOUSE_ADMIN_MAPPING = "redirect:/" + ProjectMapping.RETREAT_HOUSE.BASE_MAPPING+"/" + ProjectMapping.ADMIN;
	public static final String TEMPLATE_RETREAT_HOUSE_REPORT = ProjectMapping.RETREAT_HOUSE.BASE_MAPPING + "/" + ProjectMapping.REPORT;;

	@Autowired
	private RetreatHouseService retreatHouseService;

	@Autowired
	private AppConfig appConfig;
	
	@Autowired
	RetreatHouseController(RetreatHouseService service) {
        super(service, RETREAT_HOUSE_ATTRIBUTE_KEY);
        this.retreatHouseService = service;
    }

	/**
	 * method to register a converter to the enum type
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(AddressType.class, new EnumConverter<>(AddressType.class));
	}	
		
	@Secured({PermissionList.RETREAT_HOUSE.ADD})
	@RequestMapping(ProjectMapping.ADD)
	String newRetreatHouse(Model model) {
		
		RetreatHouse retreatHouse = new RetreatHouse();
		List<Address> addressList = new ArrayList<>(2);
		addressList.add(new Address(AddressType.S));
		addressList.add(new Address(AddressType.E));
		retreatHouse.setAddress(addressList);
		
		retreatHouse.setActive(true);
		
		model.addAttribute(RETREAT_HOUSE_ATTRIBUTE_KEY, retreatHouse);
		return TEMPLATE_RETREAT_HOUSE_ADD;
	}
	
	@Secured({PermissionList.RETREAT_HOUSE.ADD})
	@RequestMapping(value = ProjectMapping.SAVE, method = RequestMethod.POST)
	String save(@ModelAttribute @Validated({Default.class, FieldMatchGroup.class}) RetreatHouse retreat, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
		return super.save(retreat, result, model, redirectAttributes);
	}
	
	/**
	 * <p>
	 * Shows page with a RetreatHouse to edited with the id used
	 * </p>
	 * @param id
	 * @param model
	 * @return
	 * @throws ControllerRestException
	 */
	@Secured({PermissionList.RETREAT_HOUSE.EDIT})
	@RequestMapping(value = ProjectMapping.EDIT, method = RequestMethod.GET)
	String editRetreatHouse(@PathVariable("id") Long id, Model model) throws ControllerRestException {
		
		try {
			RetreatHouse retreatHouse = retreatHouseService.findOne(id);
			
			if (retreatHouse != null && retreatHouse.getAddress() != null && retreatHouse.getAddress().size() == 1) {
				retreatHouse.getAddress().add(new Address(AddressType.E));
			}
			
			model.addAttribute(RETREAT_HOUSE_ATTRIBUTE_KEY, retreatHouse);	
			
		} catch(ServiceException se){
			throw new ControllerRestException(SystemMessages.RETREAT_HOUSE_UPDATE_ERRO, se);
		} catch(Exception ex){
			throw new ControllerRestException(SystemMessages.SYSTEM_ERROR, ex);
		} 
		return TEMPLATE_RETREAT_HOUSE_EDIT;
	}
	

	/**
	 * <p>
	 * Search list companies by properties passed to the method returns to the
	 * admin list were actions like add, update delete can be taken
	 * </p>
	 *	
	 * @param model
	 * @param pageable
	 * @return
	 * @throws ControllerRestException
	 */
	@Secured(PermissionList.RETREAT_HOUSE.ADMIN)
	@RequestMapping(ProjectMapping.ADMIN)
	String adminList(			
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "responsable", required = false) String responsable,
			@RequestParam(value = "active", required = false) Boolean active, ModelMap model, Pageable pageable, HttpServletRequest request)
			throws ControllerRestException {

		listRetreatHouses(name, responsable, active, model, pageable, true, request);

		return TEMPLATE_RETREAT_HOUSE_ADMIN;
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
	@Secured(PermissionList.RETREAT_HOUSE.LIST)
	@RequestMapping(ProjectMapping.LIST)
	String list(
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "responsable", required = false) String responsable,
			@RequestParam(value = "active", required = false) Boolean active, ModelMap model, Pageable pageable, HttpServletRequest request){

		listRetreatHouses(name, responsable, active, model, pageable, false, request);

		return TEMPLATE_RETREAT_HOUSE_LIST;
	}
	
	@Secured(PermissionList.RETREAT_HOUSE.EDIT)
	@RequestMapping(value = ProjectMapping.UPDATE, method = RequestMethod.POST)
	public String update(@ModelAttribute @Validated({Default.class}) RetreatHouse retreatHouse, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
		return super.update(retreatHouse, result, model, redirectAttributes);
	}
	
	@Secured(PermissionList.RETREAT_HOUSE.DETAILS)
	@RequestMapping(value = ProjectMapping.DETAILS, method = RequestMethod.GET)
	String details(@PathVariable("id") Long id, Model model) throws JsonProcessingException, ControllerRestException {
		return super.details(id, model);
	}
	
	@Secured(PermissionList.RETREAT_HOUSE.ACTIVE_INACTIVE)
	@RequestMapping(value = ProjectMapping.ACTIVE_INACTIVE, method=RequestMethod.PUT)
	ResponseEntity<String> activate(@RequestParam(value="id") Long id) throws ControllerRestException {
		return super.activate(id);
	}
	
	@Secured(PermissionList.RETREAT_HOUSE.DELETE)
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
	@Secured(PermissionList.RETREAT_HOUSE.REPORT)
	@RequestMapping(ProjectMapping.REPORT)
	String report(
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "responsable", required = false) String responsable,
			@RequestParam(value = "active", required = false) Boolean active, ModelMap model, Pageable pageable, HttpServletRequest request){

		listRetreatHouses(name, responsable, active, model, pageable, false, request);

		return TEMPLATE_RETREAT_HOUSE_REPORT;
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
	private void listRetreatHouses(String name, String responsable, Boolean active, ModelMap model,
			Pageable pageable, boolean isAdministrative, HttpServletRequest request) {

		StringBuffer url = new StringBuffer();
		MetaEntity<RetreatHouse> metadata	= null;
		Pageable newPageable 			= PageableUtil.getPageRequest(pageable, SystemMessages.ITEMS_PER_PAGE);
		Page<RetreatHouse> cPage 		= new PageImpl<>(new ArrayList<RetreatHouse>());
		try {
			metadata	= new MetaEntity<>(RetreatHouse.class, appConfig);

			url = request.getRequestURL().append(request.getQueryString() != null ? "?"+request.getQueryString() : "");

			cPage = retreatHouseService.findByNameAndResponsableAndActiveContaining(name, responsable, active, newPageable);

			PageWrapper<RetreatHouse> page = new PageWrapper<>(cPage, url.toString(), metadata, pageable.getSort());

			model.addAttribute("page", page);
		} catch (ServiceException | MetadataException me) {
			logger.error(ERROR_OBTAINING_DATA, me);
			createReturnExceptionList(model, pageable, url.toString(), metadata, cPage, me.getMessage());
		} catch (Exception ex) {
			logger.error(ERROR_OBTAINING_DATA, ex);
			createReturnExceptionList(model, pageable, url.toString(), metadata, cPage, SystemMessages.SYSTEM_ERROR);
		}
	}
	
	private void createReturnExceptionList(ModelMap model, Pageable pageable, String url,
			MetaEntity<RetreatHouse> metadata, Page<RetreatHouse> cPage, String message) {
		
		model.addAttribute(SystemMessages.MESSAGE_ATTRIBUTE, new Message(message, Message.Type.DANGER));
		PageWrapper<RetreatHouse> page = new PageWrapper<>(cPage, url, metadata, pageable.getSort());
		model.addAttribute("page", page);
	}
}
