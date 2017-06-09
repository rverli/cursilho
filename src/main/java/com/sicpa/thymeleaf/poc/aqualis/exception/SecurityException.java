package com.sicpa.thymeleaf.poc.aqualis.exception;

public class SecurityException  extends Exception {

	private static final long serialVersionUID = -5805275040320471163L;

	public SecurityException(String message){
		super(message);
	}
	
	public SecurityException(String message, Throwable throwable){
		super(message, throwable);
	}
	
	public SecurityException(Throwable throwable){
		super(throwable);
	}

}