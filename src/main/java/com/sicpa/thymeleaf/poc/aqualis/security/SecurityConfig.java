package com.sicpa.thymeleaf.poc.aqualis.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private LoginSuccessHandler loginSuccessHandler;
	
	@Autowired
	private LogoutSuccessHandler logoutSuccessHandler;
	
	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws com.sicpa.thymeleaf.poc.aqualis.exception.SecurityException {
		try {
			auth.userDetailsService(userDetailsService).passwordEncoder(passwordencoder());
		} catch (Exception e) {
			throw new SecurityException(e);
		}
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.authorizeRequests()
					.antMatchers("/", "/home", "/webjars/**", "/css/**", "/img/**", "/h2-console/**", "/error", "/stamp/**", "/requestaccess/**").permitAll()
					.anyRequest().authenticated()
				.and()
					.formLogin()
					.loginPage("/login")
					.defaultSuccessUrl("/")
					.successHandler(loginSuccessHandler)
					.permitAll()
				.and()
					.logout()
					.logoutUrl("/logout")
					.invalidateHttpSession(true)
					.addLogoutHandler(logoutSuccessHandler)
					.permitAll()
				.and()
					.exceptionHandling().accessDeniedPage("/error/router?q=access-denied")
				.and()
					.csrf().disable()
					.headers().frameOptions().disable();
				
	}

	@Bean
	public SpringSecurityDialect getSpringSecurityDialect(){
		return new SpringSecurityDialect();
	}

	@Bean(name = "passwordEncoder")
	public PasswordEncoder passwordencoder() {
		return new BCryptPasswordEncoder();
	}
}
