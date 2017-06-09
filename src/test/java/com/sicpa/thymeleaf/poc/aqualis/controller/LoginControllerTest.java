package com.sicpa.thymeleaf.poc.aqualis.controller;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import com.sicpa.thymeleaf.poc.aqualis.config.TestConfig;
import com.sicpa.thymeleaf.poc.aqualis.exception.ServiceException;
import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.User;
import com.sicpa.thymeleaf.poc.aqualis.service.UserService;
import com.sicpa.thymeleaf.poc.aqualis.utils.ProjectMapping;



@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestConfig.class)
@WebAppConfiguration
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
public class LoginControllerTest {
	
	static final String TEMPLATE_LOGIN_EDIT = ProjectMapping.LOGIN.BASE_MAPPING+"/edit";

    private MockMvc mockMvc;

	@Mock
	private UserService userService;
	
	@Autowired
	private PageableHandlerMethodArgumentResolver pageableArgumentResolver;
	
	//@Autowired
	//private WebApplicationContext context;

	@InjectMocks
	private LoginController loginController;

	@Before
	public void setUp() {
		
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(loginController).setCustomArgumentResolvers(pageableArgumentResolver).build();
	}
	
	@After
	public void tearDown() {
		Mockito.reset(userService); 
	}
	
	@Test
	public void login() throws Exception {
		
		mockMvc.perform(MockMvcRequestBuilders.get("/login"))
	            .andExpect(MockMvcResultMatchers.status().isOk())
	            .andExpect(MockMvcResultMatchers.forwardedUrl("userLogin"))
	            .andReturn();
	}
	
	@Test
	public void editLogin() throws Exception {
		
		User user = new User(1l);
		user.setActive(true);
		user.setDeleted(false);
		user.setPassword("password");
		user.setEmail("teste@teste.com");
		user.setName("user");
		user.setPhoneNumber("(21) 98754-5546");
        
		UserAuthenticationHelper.mockLoggedUser(user);
        
		mockMvc.perform(MockMvcRequestBuilders.get("/login/edit"));
		
		MockMvcResultMatchers.model().attributeExists("user");
	}
	
	@Test(expected=NestedServletException.class)
	public void editLoginException() throws Exception {
		
		Authentication aut = Mockito.mock(Authentication.class);
		SecurityContextHolder.getContext().setAuthentication(aut);
		Mockito.doThrow(ServiceException.class).when(aut).getPrincipal();
		
		mockMvc.perform(MockMvcRequestBuilders.get("/login/edit"));
		
	}
	
	@Test
	public void updateLoginOldPassNull() throws Exception {
		
		Mockito.doNothing().when(userService).edit((User) Mockito.any());
		
		User user = new User(1l);
		user.setActive(true);
		user.setDeleted(false);
		user.setEmail("teste@teste.com");
		user.setName("user");
		user.setPhoneNumber("(21) 98754-5546"); 
		
		mockMvc.perform(MockMvcRequestBuilders.post("/login/"+ProjectMapping.UPDATE).flashAttr("user", user))
		.andExpect(MockMvcResultMatchers.view().name("login/edit"));
		
		MockMvcResultMatchers.model().attributeExists("message");
		MockMvcResultMatchers.model().attributeExists("user");
	}
	
	@Test
	public void updateLoginResultHasErro() throws Exception {
		
		Mockito.doNothing().when(userService).edit((User) Mockito.any());
		
		User user = new User(1l);
		user.setActive(true);
		user.setDeleted(false);
		user.setEmail("teste@teste.com");
		user.setName("user");
		user.setPhoneNumber("(21) 98754-5546"); 
		
		mockMvc.perform(MockMvcRequestBuilders.post("/login/"+ProjectMapping.UPDATE).param("oldPassword", "admin").flashAttr("user", user))
		.andExpect(MockMvcResultMatchers.view().name("login/edit"));
		
		MockMvcResultMatchers.model().attributeExists("message");
		MockMvcResultMatchers.model().attributeExists("user");
	}
	
	@Test
	public void updateLoginSuccess() throws Exception {
		
		User user = new User(1l);
		user.setPassword("password");
		user.setConfirmPassword("password");
		user.setActive(true);
		user.setDeleted(false);
		user.setEmail("teste@teste.com");
		user.setName("user");
		user.setPhoneNumber("(21) 98754-5546"); 
		
		mockMvc.perform(MockMvcRequestBuilders.post("/login/"+ProjectMapping.UPDATE).param("oldPassword", "admin").flashAttr("user", user))
		.andExpect(MockMvcResultMatchers.view().name("login/edit"));
		
		MockMvcResultMatchers.model().attributeExists("message");
		
		Mockito.verify(userService).findOne((Long) Mockito.any());
		Mockito.verify(userService).updatePassword((User) Mockito.any(), (String) Mockito.any(), (String) Mockito.any());
	}
	
	@Test
	public void updateLoginException() throws Exception {
		
		Mockito.doThrow(ServiceException.class).when(userService).findOne((Long) Mockito.any());
		
		User user = new User(1l);
		user.setPassword("password");
		user.setConfirmPassword("password");
		user.setActive(true);
		user.setDeleted(false);
		user.setEmail("teste@teste.com");
		user.setName("user");
		user.setPhoneNumber("(21) 98754-5546"); 
		
		mockMvc.perform(MockMvcRequestBuilders.post("/login/"+ProjectMapping.UPDATE).param("oldPassword", "admin").flashAttr("user", user));
		
		MockMvcResultMatchers.model().attributeExists("message");
		MockMvcResultMatchers.model().attributeExists("user");
		
		Mockito.verify(userService).findOne((Long) Mockito.any());
		Mockito.verifyNoMoreInteractions(userService);
	}


}
