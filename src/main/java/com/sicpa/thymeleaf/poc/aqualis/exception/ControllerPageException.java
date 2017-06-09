package com.sicpa.thymeleaf.poc.aqualis.exception;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class ControllerPageException extends Exception {

	private static final long serialVersionUID = 7187086265192602256L;
	
	private final String url;
	
	private final transient RedirectAttributes redirectAttributes;

	public ControllerPageException(String url, RedirectAttributes attributes) {
		this.url = url;
		this.redirectAttributes = attributes;
	}
	
	public ControllerPageException(RedirectAttributes attributes) {
		this.redirectAttributes = attributes;
		this.url = "";
	}
	
	public String getUrl() {
		return url;
	}

	public RedirectAttributes getRedirectAttributes() {
		return redirectAttributes;
	}


}
