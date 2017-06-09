package com.sicpa.thymeleaf.poc.aqualis.exception;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sicpa.thymeleaf.poc.aqualis.messages.ObjectMapperUtil;

/**
* REST exception handlers defined at a global level for the application
**/
@PropertySources(value = {@PropertySource("classpath:messages.properties")})
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	
	private static final Logger loggerRestResponse = Logger.getLogger(RestResponseEntityExceptionHandler.class);

	@ExceptionHandler(value = { ControllerPageException.class })
	protected String handleUnknownException(ControllerPageException ex) {
		
		loggerRestResponse.error(ex.getMessage(), ex);
		return "forward:/user/feedback";
	}
	
	@ExceptionHandler(value = { ControllerRestException.class })
	protected ResponseEntity<? extends Object> controllerRestException(ControllerRestException ex) {
		
		String message = null;
		
		try {
			message = ObjectMapperUtil.convertJsonMessage(ex.getMessage());
			loggerRestResponse.error(ex.getMessage(), ex);
		} catch (JsonProcessingException e) {
			loggerRestResponse.error(ex.getMessage(), e);
		}
		return new ResponseEntity<>(message, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
 
    @ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class})
    protected ResponseEntity<String> handleConflict(RuntimeException ex) {
    	
    	String message = null;
		
		try {
			message = ObjectMapperUtil.convertJsonMessage(ex.getMessage());
			loggerRestResponse.error(ex.getMessage(), ex);
		} catch (JsonProcessingException e) {
			loggerRestResponse.error(ex.getMessage(), e);
		}
    	return new ResponseEntity<>(message, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
}
