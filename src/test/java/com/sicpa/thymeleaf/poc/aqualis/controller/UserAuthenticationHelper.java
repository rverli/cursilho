package com.sicpa.thymeleaf.poc.aqualis.controller;

import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.User;

public class UserAuthenticationHelper {

	public static void mockLoggedUser(User user) {
		Authentication aut = Mockito.mock(Authentication.class);
		SecurityContextHolder.getContext().setAuthentication(aut);
		
		Mockito.when(aut.getPrincipal()).thenReturn(user);
	}
	
}
