package com.sicpa.thymeleaf.poc.aqualis.utils;

import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * Tests to EmailUtil utility class
 * @author lrosa1
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class EmailUtilTest {
	
	@InjectMocks
	private EmailUtil emailUtil;
	
	@Mock
	private JavaMailSender javaMailSender;
	
	@Mock
	private VelocityEngine velocityEngine; 
	
	@Test
	public void testSendWithValidParamenters() {
		Mail mail = new Mail();
		mail.setFrom("test@mail.com");
		mail.setTo("test@mail.com");
		emailUtil.send(mail);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSendWithMailObjectNull() {
		Mail mail = null;
		emailUtil.send(mail);		
	}

	@Test(expected = IllegalArgumentException.class)	
	public void testSendWithMailParamentersEmpty() {
		Mail mail = new Mail();
		emailUtil.send(mail);		
	}

	@Test(expected = IllegalArgumentException.class)	
	public void testSendWithMailFromParamentersEmpty() {
		Mail mail = new Mail();
		mail.setFrom(null);
		mail.setTo("test@mail.com");
		emailUtil.send(mail);		
	}

	@Test(expected = IllegalArgumentException.class)	
	public void testSendWithMailToParamentersEmpty() {
		Mail mail = new Mail();
		mail.setFrom("test@mail.com");
		mail.setTo(null);
		emailUtil.send(mail);		
	}

	@Test
	public void testSendThrowException() {
		Mail mail = new Mail();
		mail.setFrom("test@mail.com");
		mail.setTo("test@mail.com");
		Mockito.doThrow(Exception.class).when(javaMailSender).send(Mockito.any(SimpleMailMessage.class));
		emailUtil.send(mail);		
	}
		
	@Test
	public void testSendWithTemplateValidParamenters() {
		Mail mail = new Mail();
		mail.setFrom("test@mail.com");
		mail.setTo("test@mail.com");
		mail.setTemplatePath("valid path");
		emailUtil.sendWithTemplate((mail));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSendWithTemplateMailObjectNull() {
		Mail mail = null;
		emailUtil.sendWithTemplate((mail));		
	}

	@Test(expected = IllegalArgumentException.class)	
	public void testSendWithTemplateMailParamentersEmpty() {
		Mail mail = new Mail();
		emailUtil.sendWithTemplate((mail));		
	}

	@Test(expected = IllegalArgumentException.class)	
	public void testSendWithTemplateFromParamentersEmpty() {
		Mail mail = new Mail();
		mail.setFrom(null);
		mail.setTo("test@mail.com");
		mail.setTemplatePath("valid path");
		emailUtil.sendWithTemplate((mail));		
	}

	@Test(expected = IllegalArgumentException.class)	
	public void testSendWithTemplateToParamentersEmpty() {
		Mail mail = new Mail();
		mail.setTemplatePath("valid path");
		mail.setFrom("test@mail.com");
		mail.setTo(null);
		emailUtil.sendWithTemplate((mail));		
	}

	@Test(expected = IllegalArgumentException.class)	
	public void testSendWithTemplateTemplatePathParamentersEmpty() {
		Mail mail = new Mail();
		mail.setTemplatePath(null);
		mail.setFrom("test@mail.com");
		mail.setTo("test@mail.com");
		emailUtil.sendWithTemplate((mail));		
	}

	@Test
	public void testSendWithTemplateThrowException() {
		Mail mail = new Mail();
		mail.setTemplatePath("valid path");
		mail.setFrom("test@mail.com");
		mail.setTo("test@mail.com");
		mail.setSubject("test");
		MimeMessage mimeMessage = Mockito.mock(MimeMessage.class);
		Mockito.when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
		Mockito.doThrow(Exception.class).when(javaMailSender).send(Mockito.any(MimeMessage.class));
		emailUtil.sendWithTemplate((mail));		
	}
	
	@Test
	public void testSendWithTemplate() {
		Mail mail = new Mail();
		mail.setTemplatePath("valid path");
		mail.setFrom("test@mail.com");
		mail.setTo("test@mail.com");
		mail.setSubject("test");
		MimeMessage mimeMessage = Mockito.mock(MimeMessage.class);
		Mockito.when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
		emailUtil.sendWithTemplate((mail));
		Mockito.verify(javaMailSender).send(mimeMessage);
	}
	
}
