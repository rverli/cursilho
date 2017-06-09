package com.sicpa.thymeleaf.poc.aqualis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

	@RequestMapping(value="/", method = RequestMethod.GET)
	String root() {
		return "home";
	}
	
	@RequestMapping(value="/home", method = RequestMethod.GET)
	String home() {
		return "home";
	}
}
