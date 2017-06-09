package com.sicpa.thymeleaf.poc.aqualis.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ComponentScan(basePackages="com.sicpa.thymeleaf.poc.aqualis")
@EnableAutoConfiguration
@EnableTransactionManagement
@Configuration
public class TestConfig {

	public static void main(String[] args) {
		SpringApplication.run(TestConfig.class, args);
	}
	
	/**
	 * http://localhost:8070/
	 * @return
	 * @throws SQLException
	 */
	/*@Bean(initMethod="start", destroyMethod="stop")
	public org.h2.tools.Server h2WebServer() throws SQLException {
		return org.h2.tools.Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8070");
	}*/
	
}
