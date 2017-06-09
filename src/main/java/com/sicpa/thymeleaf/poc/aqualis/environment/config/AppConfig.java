package com.sicpa.thymeleaf.poc.aqualis.environment.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:config.properties")
public class AppConfig {

	@Autowired
	private Environment environment;

	public Environment getEnvironment() {
		return environment;
	}

	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}
	
	public String getProperty(String key) {
		return environment.getProperty(key);
	}

	public String getProperty(String key, String... values) {
		String resp = environment.getProperty(key);
		for (int i=0;i<values.length;i++) {
			resp = resp.replaceAll("\\{" + i + "\\}", values[i]);
		}
		
		return resp;
	}
}
