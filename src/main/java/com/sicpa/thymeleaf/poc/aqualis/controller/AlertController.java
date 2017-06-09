package com.sicpa.thymeleaf.poc.aqualis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sicpa.thymeleaf.poc.aqualis.messages.Message;
import com.sicpa.thymeleaf.poc.aqualis.messages.SystemMessages;

@Controller
public class AlertController {

	private static final String ALERT_TEMPLATE = "fragments/alerts :: alert";
	
	@RequestMapping(value="*/alert")
	String alert(@RequestParam(value="type") String type, @RequestParam(value=SystemMessages.MESSAGE_ATTRIBUTE) String messageTxt, Model model) {
		
		Message message = new Message(messageTxt, Message.Type.valueOf(type));
		model.addAttribute("message", message);
		
		return ALERT_TEMPLATE;
	}
	
}
