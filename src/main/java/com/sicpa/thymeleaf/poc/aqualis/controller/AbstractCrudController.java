package com.sicpa.thymeleaf.poc.aqualis.controller;

import java.io.Serializable;

import javax.validation.groups.Default;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sicpa.thymeleaf.poc.aqualis.exception.ControllerRestException;
import com.sicpa.thymeleaf.poc.aqualis.exception.ServiceException;
import com.sicpa.thymeleaf.poc.aqualis.messages.Message;
import com.sicpa.thymeleaf.poc.aqualis.messages.Message.Type;
import com.sicpa.thymeleaf.poc.aqualis.messages.MessageHelper;
import com.sicpa.thymeleaf.poc.aqualis.messages.ObjectMapperUtil;
import com.sicpa.thymeleaf.poc.aqualis.messages.SystemMessages;
import com.sicpa.thymeleaf.poc.aqualis.service.BasicCrudService;
import com.sicpa.thymeleaf.poc.aqualis.utils.ProjectMapping;
import com.sicpa.thymeleaf.poc.aqualis.utils.form.validators.fieldmatch.FieldMatchGroup;

/**
 * 
 * Abstract controller for crud operations on entities
 *
 * @param <T>
 * @param <ID>
 */
public abstract class AbstractCrudController <T, ID extends Serializable> {
	
    private Logger logger = LoggerFactory.getLogger(AbstractCrudController.class);

    private BasicCrudService<T, ID> service;

	private String field;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public AbstractCrudController(BasicCrudService service, String fild) {
		this.service = service;
		this.field = fild;
	}
	
	/**
	 * <p>
	 * Updates an entity with the details passed in
	 * </p>
	 * @param entity
	 * @param result
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@Secured(ProjectMapping.PROFILE.ADMIN)
	@RequestMapping(value = ProjectMapping.UPDATE, method = RequestMethod.POST)
	public String update(@ModelAttribute @Validated({Default.class}) T entity, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
		
		if (result.hasErrors()) {
			model.addAttribute(field, entity);
			return field + "/edit";
		} else {
			try {
				service.edit(entity);
			} catch (ServiceException e) {
				logger.error("Erro no update", e);
				model.addAttribute(field, entity);
				model.addAttribute(SystemMessages.MESSAGE_ATTRIBUTE, new Message(e.getMessage(), Type.DANGER));
				return field + "/edit";
			}
			MessageHelper.addSuccessAttribute(redirectAttributes, SystemMessages.ENTITY_UPDATE_SUCCESS);
			return "redirect:/" + field + "/" + ProjectMapping.ADMIN;
		}
	}
	
	/**
	 * <p>
	 * Saves a new user, in case of errors takes appropriate actions to inform the user. 
	 * </p>
	 * 
	 * @param user
	 * @param result
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@Secured({ProjectMapping.PROFILE.ADMIN})
	@RequestMapping(value = ProjectMapping.SAVE, method = RequestMethod.POST)
	String save(@ModelAttribute @Validated({Default.class, FieldMatchGroup.class}) T entity, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
		
		if (result.hasErrors()) {
			model.addAttribute(field, entity);
			return field + "/add";
			
		} else {
			try {
				service.save(entity);
			} catch (ServiceException e) {
				logger.error("Error saving " + field + ":" + entity.toString(), e);
				model.addAttribute(field, entity);
				model.addAttribute(SystemMessages.MESSAGE_ATTRIBUTE, new Message(e.getMessage(), Message.Type.DANGER));
				return field + "/add";
			}
			MessageHelper.addSuccessAttribute(redirectAttributes, SystemMessages.ENTITY_SAVE_SUCCESS);
			return "redirect:/" + field + "/" + ProjectMapping.ADMIN;
		}
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws ControllerRestException
	 */
	@Secured(ProjectMapping.PROFILE.ADMIN)
	@RequestMapping(value = ProjectMapping.ACTIVE_INACTIVE, method=RequestMethod.PUT)
	ResponseEntity<String> activate(ID id) throws ControllerRestException {

		if (id == null) {
			throw new IllegalArgumentException(SystemMessages.INVALID_PARAMETER);
		}
		
		String writeValueAsString = new String();
		
		try{
			
			service.activate(id);
			writeValueAsString = ObjectMapperUtil.convertJsonMessage(SystemMessages.ENTITY_UPDATE_SUCCESS);
			
		} catch(ServiceException | JsonProcessingException se){
			throw new ControllerRestException(SystemMessages.ENTITY_UPDATE_ERRO, se);
		} catch(Exception ex){
			throw new ControllerRestException(SystemMessages.SYSTEM_ERROR, ex);
		} 
		return new ResponseEntity<>(writeValueAsString, new HttpHeaders(), HttpStatus.OK);
	}

	@Secured(ProjectMapping.PROFILE.ADMIN)
	@RequestMapping(value = ProjectMapping.DELETE, method=RequestMethod.DELETE, produces=MediaType.APPLICATION_JSON_VALUE)	
	ResponseEntity<String> delete(@PathVariable(value="id") ID id) throws ControllerRestException {	

		if (id == null) {
			throw new IllegalArgumentException(SystemMessages.INVALID_PARAMETER);
		}
		
		String writeValueAsString = new String();
		try {			
			service.delete(id);
			writeValueAsString = ObjectMapperUtil.convertJsonMessage(SystemMessages.ENTITY_REMOVE_SUCCESS);
				
		} catch(ServiceException | JsonProcessingException se){
			throw new ControllerRestException(SystemMessages.ENTITY_REMOVE_ERRO, se);
		} catch(Exception ex){
			throw new ControllerRestException(SystemMessages.SYSTEM_ERROR, ex);
		} 
		return new ResponseEntity<>(writeValueAsString, new HttpHeaders(), HttpStatus.OK);
	}

	public void setService(BasicCrudService<T, ID> service) {
		this.service = service;
	}
	
	@Secured(ProjectMapping.PROFILE.USER)
	@RequestMapping(value = ProjectMapping.DETAILS, method = RequestMethod.GET)
	String details(@PathVariable("id") ID id, Model model) throws JsonProcessingException, ControllerRestException {
		
		try {
			T entity = service.findOne(id);
			model.addAttribute(field, entity);
		} catch(ServiceException se){
			logger.error(SystemMessages.ENTITY_DETAIL_ERROR, se);
			throw new ControllerRestException(ObjectMapperUtil.convertJsonMessage(SystemMessages.ENTITY_DETAIL_ERROR), se);
		} catch(Exception ex){
			logger.error(SystemMessages.SYSTEM_ERROR, ex);
			throw new ControllerRestException(ObjectMapperUtil.convertJsonMessage(SystemMessages.SYSTEM_ERROR), ex);
		} 

		return  field + "/details";
	}


}
