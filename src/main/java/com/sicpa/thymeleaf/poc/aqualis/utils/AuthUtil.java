package com.sicpa.thymeleaf.poc.aqualis.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtil {
	
	private AuthUtil() {}
	
	public static Authentication getAuthenticatedUser() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
	
}
