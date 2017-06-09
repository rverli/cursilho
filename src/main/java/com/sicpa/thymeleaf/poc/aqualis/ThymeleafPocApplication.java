package com.sicpa.thymeleaf.poc.aqualis;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.planetj.servlet.filter.compression.CompressingFilter;
import com.sicpa.thymeleaf.poc.aqualis.interceptor.TransactionInterceptor;

@SpringBootApplication
@EnableScheduling
public class ThymeleafPocApplication extends WebMvcConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(ThymeleafPocApplication.class, args);
	}

	@Bean
	public CompressingFilter compressingFilter() {
		return new CompressingFilter();
	}

	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver slr = new SessionLocaleResolver();
		slr.setDefaultLocale(Locale.ROOT);
		return slr;
	}

	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
		lci.setParamName("lang");
		return lci;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
	    registry.addInterceptor(new TransactionInterceptor()).addPathPatterns("/user/delete/*");
	    registry.addInterceptor(new TransactionInterceptor()).addPathPatterns("**/save/**");
	    registry.addInterceptor(new TransactionInterceptor()).addPathPatterns("**/update/**");
	}
	
}
