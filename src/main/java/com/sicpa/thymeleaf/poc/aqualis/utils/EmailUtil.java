package com.sicpa.thymeleaf.poc.aqualis.utils;

import java.nio.charset.StandardCharsets;

import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.hsqldb.lib.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

/**
 * Utility class to send email
 * @author lrosa1
 *
 */
@Component
public class EmailUtil {

private Logger logger = LoggerFactory.getLogger(EmailUtil.class);
	
	@Autowired
	private JavaMailSenderImpl javaMailSender;
	
	@Autowired
	private VelocityEngine velocityEngine; 
		
	/**
	 * Send email with simple text message 
	 * @param mail - object used to prepare the email
	 */
	public void send(Mail mail) {
		
		if (mail == null || StringUtil.isEmpty(mail.getFrom()) || StringUtil.isEmpty(mail.getTo())) {
			throw new IllegalArgumentException("argument 'mail', 'from' or 'to' not be null");
		}
		
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(mail.getTo());
		mailMessage.setFrom(mail.getFrom());
		mailMessage.setSubject(mail.getSubject());
		mailMessage.setText(mail.getText());
		
		try {
			javaMailSender.send(mailMessage);
		} catch (Exception ex) {
			logger.error("Error send email.", ex);
		}
	}
	
	/**
	 * Send email with a velocity template
	 * @param mail - object used to prepare the email
	 */
	public void sendWithTemplate(final Mail mail) {

		if (mail == null || StringUtil.isEmpty(mail.getFrom()) || StringUtil.isEmpty(mail.getTo()) || StringUtil.isEmpty(mail.getTemplatePath())) {
			throw new IllegalArgumentException("argument 'mail', 'from', 'to' or 'templatePath' not be null");
		}

		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
            message.setTo(mail.getTo());
            message.setFrom(mail.getFrom()); 
            message.setSubject(mail.getSubject());
            String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, mail.getTemplatePath(), StandardCharsets.UTF_8.name(), mail.getModel());
            message.setText(text, true);
			javaMailSender.send(mimeMessage);
		} catch (Exception ex) {
			logger.error("Error send email.", ex);
		}
	}
	
}
