package com.sicpa.thymeleaf.poc.aqualis.exception;

public class RepositoryException  extends Exception {

	private static final long serialVersionUID = 7187086265192602256L;

	public RepositoryException(String message){
		super(message);
	}
	
	public RepositoryException(String message, Throwable throwable){
		super(message, throwable);
	}
	
	public RepositoryException(Throwable throwable){
		super(throwable);
	}

}
