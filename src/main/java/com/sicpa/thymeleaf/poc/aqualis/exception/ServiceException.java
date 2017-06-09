package com.sicpa.thymeleaf.poc.aqualis.exception;

public class ServiceException extends Exception {

	private static final long serialVersionUID = -5482912626593491493L;

	public ServiceException(String message){
		super(message);
	}
	
	public ServiceException(String message, Throwable throwable){
		super(message, throwable);
	}
}
