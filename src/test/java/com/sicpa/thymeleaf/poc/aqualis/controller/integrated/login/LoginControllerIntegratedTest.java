package com.sicpa.thymeleaf.poc.aqualis.controller.integrated.login;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.sicpa.thymeleaf.poc.aqualis.config.TestConfig;
import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.User;
import com.sicpa.thymeleaf.poc.aqualis.service.UserService;
import com.sicpa.thymeleaf.poc.aqualis.utils.AuthUtil;
import com.sicpa.thymeleaf.poc.aqualis.utils.ProjectMapping;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestConfig.class)
@ActiveProfiles("test")
@WebAppConfiguration
@TestExecutionListeners({	DependencyInjectionTestExecutionListener.class,
        					DirtiesContextTestExecutionListener.class,
        					TransactionalTestExecutionListener.class,
        					DbUnitTestExecutionListener.class,
        					WithSecurityContextTestExecutionListener.class})
public class LoginControllerIntegratedTest {

	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext context;
	
    @Resource
    private FilterChainProxy springSecurityFilterChain;
	
	@Autowired
	private UserService userService;
	
	@Before
	public void setUp() {
		
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.webAppContextSetup(context)
				.apply(SecurityMockMvcConfigurers.springSecurity(springSecurityFilterChain))
				.build();
	}

	@Test
	@WithUserDetails("mai1l@mail.com")
    @DatabaseSetup(	type = DatabaseOperation.CLEAN_INSERT, value = "classpath:/loginControllerIntegratedTest/updateSuccess_preparing.xml")
	@DatabaseTearDown(type = DatabaseOperation.DELETE_ALL, value = "classpath:/loginControllerIntegratedTest/updateSuccess_preparing.xml")	
	public void updateSuccess() throws Exception {
		
		User user = userService.findOne(1l);
		user.setConfirmPassword("123");

		Assert.assertEquals(user.getPassword(), "$2a$06$u0PaBu0yadzAYNlL7SwoXOoLzBZ7uq5FKTtKIGQahGnVqHfnK0Ul.");
		
		mockMvc.perform(MockMvcRequestBuilders.post("/login/"+ProjectMapping.UPDATE).param("password", "12345").param("oldPassword", "123").flashAttr("user", user))
			.andExpect(SecurityMockMvcResultMatchers.authenticated().withUsername("mai1l@mail.com").withRoles("ADMIN"))
			.andExpect(MockMvcResultMatchers.view().name("login/edit"));
		
		user = userService.findOne(1l);
		Assert.assertNotEquals(user.getPassword(), "123");
	}
	
	@Test
	@WithUserDetails("mail@mail.com")
    @DatabaseSetup(	type = DatabaseOperation.CLEAN_INSERT, value = "classpath:/loginControllerIntegratedTest/logout_preparing.xml")
	@DatabaseTearDown(type = DatabaseOperation.DELETE_ALL, value = "classpath:/loginControllerIntegratedTest/logout_preparing.xml")
	public void logout() throws Exception {
		
		mockMvc = MockMvcBuilders.webAppContextSetup(context)
					.apply(SecurityMockMvcConfigurers.springSecurity(springSecurityFilterChain))
					.build();
		
		mockMvc.perform(SecurityMockMvcRequestBuilders.logout())
			.andExpect(SecurityMockMvcResultMatchers.unauthenticated())
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.redirectedUrl("/login?logout"));
		
		Assert.assertNull(AuthUtil.getAuthenticatedUser());
		
	}

	
}
