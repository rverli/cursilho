package com.sicpa.thymeleaf.poc.aqualis.exception;

import org.apache.commons.lang3.StringUtils;

public class ControllerRestException  extends Exception {

	private static final long serialVersionUID = 7187086265192602256L;
	
	private final String url;
	
	public ControllerRestException(String message){
		super(message);
		this.url = StringUtils.EMPTY;
	}
	
	public ControllerRestException(String message, Throwable throwable){
		super(message, throwable);
		this.url = StringUtils.EMPTY;
	}
	
	public ControllerRestException(Throwable throwable){
		super(throwable);
		this.url = StringUtils.EMPTY;
	}

	public String getUrl() {
		return url;
	}

}
