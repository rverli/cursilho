package com.sicpa.thymeleaf.poc.aqualis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/error")
public class ErrorController {
	
	@RequestMapping(value = "/router", method = RequestMethod.GET)
	public String errorRouter(@RequestParam("q") String resource) {
		return "redirect:/error/" + resource;
	}
	
	@RequestMapping(value = "/access-denied", method = RequestMethod.GET)
	public String accessDenied() {
		return "/error/accessDenied";
	}
}