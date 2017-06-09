package com.sicpa.thymeleaf.poc.aqualis.exception;

public class MetadataException extends Exception {

	private static final long serialVersionUID = -5482912626593491493L;

	public MetadataException(String message){
		super(message);
	}
	
	public MetadataException(String message, Throwable throwable){
		super(message, throwable);
	}
}
