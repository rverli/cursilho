package com.sicpa.thymeleaf.poc.aqualis.utils;

import java.util.Map;

/**
 * Object used to prepare the email
 * @author lrosa1
 *
 */
public class Mail {

	private String from;
	private String to;
	private String subject;
	private String text;
	private String templatePath;
	private Map<String, Object> model;

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getTemplatePath() {
		return templatePath;
	}

	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}

	public Map<String, Object> getModel() {
		return model;
	}

	public void setModel(Map<String, Object> model) {
		this.model = model;
	}

	@Override
	public String toString() {
		return "Mail [from=" + from + ", to=" + to + ", subject=" + subject + ", templatePath=" + templatePath + "]";
	}
	
}
