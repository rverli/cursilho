package com.sicpa.thymeleaf.poc.aqualis.controller.integrated.user;

import static org.junit.Assert.assertNotNull;

import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.HttpHeaders;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.sicpa.thymeleaf.poc.aqualis.config.TestConfig;
import com.sicpa.thymeleaf.poc.aqualis.controller.UserController;
import com.sicpa.thymeleaf.poc.aqualis.exception.RestResponseEntityExceptionHandler;
import com.sicpa.thymeleaf.poc.aqualis.messages.SystemMessages;
import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.User;
import com.sicpa.thymeleaf.poc.aqualis.persistence.repository.UserRepository;
import com.sicpa.thymeleaf.poc.aqualis.utils.PageWrapper;
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
public class UserControllerIntegratedTest {

	@Autowired
	private UserController userController;
	
	private MockMvc mockMvc;

	private MvcResult resultService;
	
	@Autowired
	private PageableHandlerMethodArgumentResolver pageableArgumentResolver;
	
	@Autowired
	private UserRepository userRepository;
	
	@Test
	@SuppressWarnings("unchecked")
	@DatabaseSetup(	type = DatabaseOperation.CLEAN_INSERT, value = "classpath:/UserControllerIntegratedTest/listUsers_preparing.xml")
	@DatabaseTearDown(	type = DatabaseOperation.DELETE_ALL, value = "classpath:/UserControllerIntegratedTest/listUsers_preparing.xml")
	public void listUsers() throws Exception {
		
		mockMvc = MockMvcBuilders.standaloneSetup(userController)
				.setCustomArgumentResolvers(pageableArgumentResolver).build();
		
		resultService = mockMvc.perform(MockMvcRequestBuilders.get("/user/list"))
		            .andExpect(MockMvcResultMatchers.status().isOk())
		            .andExpect(MockMvcResultMatchers.view().name("user/list"))
		            .andReturn();
		
		assertNotNull(resultService);
		
		Map<String, Object> model = resultService.getModelAndView().getModel();
		assertNotNull(model);
		
		PageWrapper<User> pageWrapper = (PageWrapper<User>)model.get("page");
		assertNotNull(pageWrapper);
		Assert.assertEquals(1, pageWrapper.getNumber());
		Assert.assertEquals(10, pageWrapper.getSize());
		Assert.assertEquals(1, pageWrapper.getTotalPages());
		Assert.assertTrue(pageWrapper.isFirstPage());
		Assert.assertTrue(pageWrapper.isLastPage());
		Assert.assertEquals(2, pageWrapper.getContent().size());
	}
	
	@Test
	@SuppressWarnings("unchecked")
    @DatabaseSetup(	type = DatabaseOperation.CLEAN_INSERT, value = "classpath:/UserControllerIntegratedTest/listUsers_UsingPagination_preparing.xml")
	@DatabaseTearDown(	type = DatabaseOperation.DELETE_ALL, value = "classpath:/UserControllerIntegratedTest/listUsers_UsingPagination_preparing.xml")
	public void listUsers_UsingPagination() throws Exception {
		
		mockMvc = MockMvcBuilders.standaloneSetup(userController).setCustomArgumentResolvers(pageableArgumentResolver).build();
		
		resultService = mockMvc.perform(MockMvcRequestBuilders.get("/user/list"))
		            .andExpect(MockMvcResultMatchers.status().isOk())
		            .andExpect(MockMvcResultMatchers.view().name("user/list"))
		            .andReturn();
		
		assertNotNull(resultService);
		
		Map<String, Object> model = resultService.getModelAndView().getModel();
		assertNotNull(model);

		PageWrapper<User> pageWrapper = (PageWrapper<User>)model.get("page");
		assertNotNull(pageWrapper);
		Assert.assertEquals(1, pageWrapper.getNumber());
		Assert.assertEquals(10, pageWrapper.getSize());
		Assert.assertEquals(2, pageWrapper.getTotalPages());
		Assert.assertEquals(10, pageWrapper.getContent().size());
	}
	
	@Test
	@SuppressWarnings("unchecked")
    @DatabaseSetup(	type = DatabaseOperation.CLEAN_INSERT, value = "classpath:/UserControllerIntegratedTest/listUsers_UsingPagination_SecondPage_preparing.xml")
	@DatabaseTearDown(	type = DatabaseOperation.DELETE_ALL, value = "classpath:/UserControllerIntegratedTest/listUsers_UsingPagination_SecondPage_preparing.xml")
	public void listUsers_UsingPagination_SecondPage() throws Exception {
		
		mockMvc = MockMvcBuilders.standaloneSetup(userController).setCustomArgumentResolvers(pageableArgumentResolver).build();
		
		resultService = mockMvc.perform(MockMvcRequestBuilders.get("/user/list?page=1"))
		            .andExpect(MockMvcResultMatchers.status().isOk())
		            .andExpect(MockMvcResultMatchers.view().name("user/list"))
		            .andReturn();
		
		assertNotNull(resultService);
		
		Map<String, Object> model = resultService.getModelAndView().getModel();
		assertNotNull(model);
		
		PageWrapper<User> pageWrapper = (PageWrapper<User>)model.get("page");
		assertNotNull(pageWrapper);
		Assert.assertEquals(2, pageWrapper.getNumber());
		Assert.assertEquals(10, pageWrapper.getSize());
		Assert.assertEquals(2, pageWrapper.getTotalPages());
		Assert.assertEquals(10, pageWrapper.getContent().size());
	}
	
	@Test
	@SuppressWarnings("unchecked")
    @DatabaseSetup(	type = DatabaseOperation.CLEAN_INSERT, value = "classpath:/UserControllerIntegratedTest/listUsers_SearchByName_preparing.xml")
	@DatabaseTearDown(	type = DatabaseOperation.DELETE_ALL, value = "classpath:/UserControllerIntegratedTest/listUsers_SearchByName_preparing.xml")
	public void listUsers_SearchByName() throws Exception {
		
		mockMvc = MockMvcBuilders.standaloneSetup(userController).setCustomArgumentResolvers(pageableArgumentResolver).build();
		
		resultService = mockMvc.perform(MockMvcRequestBuilders.get("/user/list").param("name", "1"))
		            .andExpect(MockMvcResultMatchers.status().isOk())
		            .andExpect(MockMvcResultMatchers.view().name("user/list"))
		            .andReturn();
		
		assertNotNull(resultService);
		
		Map<String, Object> model = resultService.getModelAndView().getModel();
		assertNotNull(model);
		
		PageWrapper<User> pageWrapper = (PageWrapper<User>)model.get("page");
		assertNotNull(pageWrapper);
		Assert.assertEquals(1, pageWrapper.getNumber());
		Assert.assertEquals(10, pageWrapper.getSize());
		Assert.assertEquals(2, pageWrapper.getTotalPages());
		Assert.assertEquals(10, pageWrapper.getContent().size());
	}
	
	@Test
	@SuppressWarnings("unchecked")
    @DatabaseSetup(	type = DatabaseOperation.CLEAN_INSERT, value = "classpath:/UserControllerIntegratedTest/listUsers_SearchByEmail_preparing.xml")
	@DatabaseTearDown(	type = DatabaseOperation.DELETE_ALL, value = "classpath:/UserControllerIntegratedTest/listUsers_SearchByEmail_preparing.xml")
	public void listUsers_SearchByEmail() throws Exception {
		
		mockMvc = MockMvcBuilders.standaloneSetup(userController).setCustomArgumentResolvers(pageableArgumentResolver).build();
		
		resultService = mockMvc.perform(MockMvcRequestBuilders.get("/user/list?email=0"))
		            .andExpect(MockMvcResultMatchers.status().isOk())
		            .andExpect(MockMvcResultMatchers.view().name("user/list"))
		            .andReturn();
		
		assertNotNull(resultService);
		
		Map<String, Object> model = resultService.getModelAndView().getModel();
		assertNotNull(model);

		PageWrapper<User> pageWrapper = (PageWrapper<User>)model.get("page");
		assertNotNull(pageWrapper);
		Assert.assertEquals(1, pageWrapper.getNumber());
		Assert.assertEquals(10, pageWrapper.getSize());
		Assert.assertEquals(1, pageWrapper.getTotalPages());
		Assert.assertEquals(2, pageWrapper.getContent().size());
	}
	
	@Test
	@SuppressWarnings("unchecked")
    @DatabaseSetup(	type = DatabaseOperation.CLEAN_INSERT, value = "classpath:/UserControllerIntegratedTest/listUsers_UsingPagination_PageNotExistent_preparing.xml")
	@DatabaseTearDown(	type = DatabaseOperation.DELETE_ALL, value = "classpath:/UserControllerIntegratedTest/listUsers_UsingPagination_PageNotExistent_preparing.xml")
	public void listUsers_UsingPagination_PageNotExistent() throws Exception {
		
		mockMvc = MockMvcBuilders.standaloneSetup(userController).setCustomArgumentResolvers(pageableArgumentResolver).build();
		
		resultService = mockMvc.perform(MockMvcRequestBuilders.get("/user/admin?page=100"))
		            .andExpect(MockMvcResultMatchers.status().isOk())
		            .andExpect(MockMvcResultMatchers.view().name("user/admin"))
		            .andReturn();
		
		assertNotNull(resultService);
		
		Map<String, Object> model = resultService.getModelAndView().getModel();
		assertNotNull(model);
		
		PageWrapper<User> pageWrapper = (PageWrapper<User>)model.get("page");
		assertNotNull(pageWrapper);
		Assert.assertEquals(101, pageWrapper.getNumber());
		Assert.assertEquals(10, pageWrapper.getSize());
		Assert.assertEquals(2, pageWrapper.getTotalPages());
		Assert.assertEquals(0, pageWrapper.getContent().size());
	}

	@Test
    @DatabaseSetup(	type = DatabaseOperation.CLEAN_INSERT, value = "classpath:/UserControllerIntegratedTest/delete_ValidId.xml")
	@DatabaseTearDown(	type = DatabaseOperation.DELETE_ALL, value = "classpath:/UserControllerIntegratedTest/delete_ValidId.xml")
	public void delete_ValidId() throws Exception {

		mockMvc = MockMvcBuilders.standaloneSetup(userController).setCustomArgumentResolvers(pageableArgumentResolver).build();
		
		resultService = mockMvc.perform(MockMvcRequestBuilders.delete("/user/delete/{id}", "1"))
		            .andExpect(MockMvcResultMatchers.status().isOk())
		            .andExpect( MockMvcResultMatchers.jsonPath("$.message").value(Matchers.containsString(SystemMessages.ENTITY_REMOVE_SUCCESS)))
		            .andReturn();
		
		assertNotNull(resultService);
		
		
	}

	@Test
    @DatabaseSetup(	type = DatabaseOperation.CLEAN_INSERT, value = "classpath:/UserControllerIntegratedTest/delete_InvalidId.xml")
	@DatabaseTearDown(	type = DatabaseOperation.DELETE_ALL, value = "classpath:/UserControllerIntegratedTest/delete_InvalidId.xml")
	public void delete_InvalidId() throws Exception {

		mockMvc = MockMvcBuilders.standaloneSetup(userController)
				.setControllerAdvice(new RestResponseEntityExceptionHandler() )
				.setCustomArgumentResolvers(pageableArgumentResolver).build();
		
		String invalidId = null;
		resultService = mockMvc.perform(MockMvcRequestBuilders.delete("/user/delete/{id}", invalidId))
		            .andReturn();

		assertNotNull(resultService);
				
	}

	@Test
    @DatabaseSetup(	type = DatabaseOperation.CLEAN_INSERT, value = "classpath:/UserControllerIntegratedTest/delete_InvalidId.xml")
	@DatabaseTearDown(	type = DatabaseOperation.DELETE_ALL, value = "classpath:/UserControllerIntegratedTest/delete_InvalidId.xml")
	public void delete_UserNotFound() throws Exception {

		mockMvc = MockMvcBuilders.standaloneSetup(userController)
				.setControllerAdvice(new RestResponseEntityExceptionHandler() )
				.setCustomArgumentResolvers(pageableArgumentResolver)
				.build();
		
		String idUserNotFound = "300";
		resultService = mockMvc.perform(MockMvcRequestBuilders.delete("/user/delete/{id}", idUserNotFound))
					.andExpect(MockMvcResultMatchers.status().is5xxServerError())
					.andExpect(MockMvcResultMatchers.jsonPath("$.message").value( Matchers.containsString(SystemMessages.ENTITY_REMOVE_ERRO)))
		            .andReturn();
		
		Assert.assertNotNull( resultService );
		
	}

	
	@Test
    @DatabaseSetup(	type = DatabaseOperation.CLEAN_INSERT, value = "classpath:/UserRepositoryTest/activateSuccess_preparing.xml")
	@DatabaseTearDown(	type = DatabaseOperation.DELETE_ALL, value = "classpath:/UserRepositoryTest/activateSuccess_preparing.xml")
	public void activateSuccess() throws Exception {
		
		mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
		
		resultService = mockMvc.perform(MockMvcRequestBuilders.put("/user/active?id=1"))
	            .andExpect(MockMvcResultMatchers.status().isOk())
	            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value( Matchers.containsString(SystemMessages.ENTITY_UPDATE_SUCCESS)))
	            .andReturn();
		
		Assert.assertEquals(false, userRepository.findOne(1l).isActive());
	}
	
	@Test
	@WithUserDetails("mail@mail.com")
    @DatabaseSetup(	type = DatabaseOperation.CLEAN_INSERT, value = "classpath:/UserRepositoryTest/updateSuccess_preparing.xml")
	@ExpectedDatabase(value = "classpath:/UserRepositoryTest/updateSuccess_assert.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	@DatabaseTearDown(type = DatabaseOperation.DELETE_ALL, value = "classpath:/UserRepositoryTest/updateSuccess_assert.xml")
	@DirtiesContext(classMode=ClassMode.AFTER_EACH_TEST_METHOD)
	public void updateSuccess() throws Exception {

		mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

		User user = new User(1l);
		user.setActive(false);
		//user.setPassword("password");
		user.setDeleted(false);
		user.setEmail("mail2@mail.com");
		user.setName("user 01B");
		user.setPhoneNumber("(21) 11111-1111"); 		
		
		mockMvc.perform(MockMvcRequestBuilders.post("/user/update")	
				.header(HttpHeaders.REFERER, "/" + ProjectMapping.USER.BASE_MAPPING + "/edit/1")
				.flashAttr("user", user))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.redirectedUrl("/user/admin"))
				.andReturn();
	}	













	@Test
	@SuppressWarnings("unchecked")
	@DatabaseSetup(	type = DatabaseOperation.CLEAN_INSERT, value = "classpath:/UserControllerIntegratedTest/userReport_preparing.xml")
	@DatabaseTearDown(	type = DatabaseOperation.DELETE_ALL, value = "classpath:/UserControllerIntegratedTest/userReport_preparing.xml")
	public void userReport() throws Exception {
		
		mockMvc = MockMvcBuilders.standaloneSetup(userController)
				.setCustomArgumentResolvers(pageableArgumentResolver).build();
		
		resultService = mockMvc.perform(MockMvcRequestBuilders.get("/user/report"))
		            .andExpect(MockMvcResultMatchers.status().isOk())
		            .andExpect(MockMvcResultMatchers.view().name("user/report"))
		            .andReturn();
		
		assertNotNull(resultService);
		
		Map<String, Object> model = resultService.getModelAndView().getModel();
		assertNotNull(model);
		
		PageWrapper<User> pageWrapper = (PageWrapper<User>)model.get("page");
		assertNotNull(pageWrapper);
		Assert.assertEquals(1, pageWrapper.getNumber());
		Assert.assertEquals(10, pageWrapper.getSize());
		Assert.assertEquals(1, pageWrapper.getTotalPages());
		Assert.assertTrue(pageWrapper.isFirstPage());
		Assert.assertTrue(pageWrapper.isLastPage());
		Assert.assertEquals(2, pageWrapper.getContent().size());
	}
	
	@Test
	@SuppressWarnings("unchecked")
    @DatabaseSetup(	type = DatabaseOperation.CLEAN_INSERT, value = "classpath:/UserControllerIntegratedTest/userReport_UsingPagination_preparing.xml")
	@DatabaseTearDown(	type = DatabaseOperation.DELETE_ALL, value = "classpath:/UserControllerIntegratedTest/userReport_UsingPagination_preparing.xml")
	public void userReport_UsingPagination() throws Exception {
		
		mockMvc = MockMvcBuilders.standaloneSetup(userController).setCustomArgumentResolvers(pageableArgumentResolver).build();
		
		resultService = mockMvc.perform(MockMvcRequestBuilders.get("/user/report"))
		            .andExpect(MockMvcResultMatchers.status().isOk())
		            .andExpect(MockMvcResultMatchers.view().name("user/report"))
		            .andReturn();
		
		assertNotNull(resultService);
		
		Map<String, Object> model = resultService.getModelAndView().getModel();
		assertNotNull(model);

		PageWrapper<User> pageWrapper = (PageWrapper<User>)model.get("page");
		assertNotNull(pageWrapper);
		Assert.assertEquals(1, pageWrapper.getNumber());
		Assert.assertEquals(10, pageWrapper.getSize());
		Assert.assertEquals(2, pageWrapper.getTotalPages());
		Assert.assertEquals(10, pageWrapper.getContent().size());
	}
	
	@Test
	@SuppressWarnings("unchecked")
    @DatabaseSetup(	type = DatabaseOperation.CLEAN_INSERT, value = "classpath:/UserControllerIntegratedTest/userReport_UsingPagination_SecondPage_preparing.xml")
	@DatabaseTearDown(	type = DatabaseOperation.DELETE_ALL, value = "classpath:/UserControllerIntegratedTest/userReport_UsingPagination_SecondPage_preparing.xml")
	public void userReport_UsingPagination_SecondPage() throws Exception {
		
		mockMvc = MockMvcBuilders.standaloneSetup(userController).setCustomArgumentResolvers(pageableArgumentResolver).build();
		
		resultService = mockMvc.perform(MockMvcRequestBuilders.get("/user/report?page=1"))
		            .andExpect(MockMvcResultMatchers.status().isOk())
		            .andExpect(MockMvcResultMatchers.view().name("user/report"))
		            .andReturn();
		
		assertNotNull(resultService);
		
		Map<String, Object> model = resultService.getModelAndView().getModel();
		assertNotNull(model);
		
		PageWrapper<User> pageWrapper = (PageWrapper<User>)model.get("page");
		assertNotNull(pageWrapper);
		Assert.assertEquals(2, pageWrapper.getNumber());
		Assert.assertEquals(10, pageWrapper.getSize());
		Assert.assertEquals(2, pageWrapper.getTotalPages());
		Assert.assertEquals(10, pageWrapper.getContent().size());
	}
	
	@Test
	@SuppressWarnings("unchecked")
    @DatabaseSetup(	type = DatabaseOperation.CLEAN_INSERT, value = "classpath:/UserControllerIntegratedTest/userReport_SearchByName_preparing.xml")
	@DatabaseTearDown(	type = DatabaseOperation.DELETE_ALL, value = "classpath:/UserControllerIntegratedTest/userReport_SearchByName_preparing.xml")
	public void userReport_SearchByName() throws Exception {
		
		mockMvc = MockMvcBuilders.standaloneSetup(userController).setCustomArgumentResolvers(pageableArgumentResolver).build();
		
		resultService = mockMvc.perform(MockMvcRequestBuilders.get("/user/report").param("name", "1"))
		            .andExpect(MockMvcResultMatchers.status().isOk())
		            .andExpect(MockMvcResultMatchers.view().name("user/report"))
		            .andReturn();
		
		assertNotNull(resultService);
		
		Map<String, Object> model = resultService.getModelAndView().getModel();
		assertNotNull(model);
		
		PageWrapper<User> pageWrapper = (PageWrapper<User>)model.get("page");
		assertNotNull(pageWrapper);
		Assert.assertEquals(1, pageWrapper.getNumber());
		Assert.assertEquals(10, pageWrapper.getSize());
		Assert.assertEquals(2, pageWrapper.getTotalPages());
		Assert.assertEquals(10, pageWrapper.getContent().size());
	}
	
	@Test
	@SuppressWarnings("unchecked")
    @DatabaseSetup(	type = DatabaseOperation.CLEAN_INSERT, value = "classpath:/UserControllerIntegratedTest/userReport_SearchByEmail_preparing.xml")
	@DatabaseTearDown(	type = DatabaseOperation.DELETE_ALL, value = "classpath:/UserControllerIntegratedTest/userReport_SearchByEmail_preparing.xml")
	public void userReport_SearchByEmail() throws Exception {
		
		mockMvc = MockMvcBuilders.standaloneSetup(userController).setCustomArgumentResolvers(pageableArgumentResolver).build();
		
		resultService = mockMvc.perform(MockMvcRequestBuilders.get("/user/report?email=0"))
		            .andExpect(MockMvcResultMatchers.status().isOk())
		            .andExpect(MockMvcResultMatchers.view().name("user/report"))
		            .andReturn();
		
		assertNotNull(resultService);
		
		Map<String, Object> model = resultService.getModelAndView().getModel();
		assertNotNull(model);

		PageWrapper<User> pageWrapper = (PageWrapper<User>)model.get("page");
		assertNotNull(pageWrapper);
		Assert.assertEquals(1, pageWrapper.getNumber());
		Assert.assertEquals(10, pageWrapper.getSize());
		Assert.assertEquals(1, pageWrapper.getTotalPages());
		Assert.assertEquals(2, pageWrapper.getContent().size());
	}
	
	@Test
	@SuppressWarnings("unchecked")
    @DatabaseSetup(	type = DatabaseOperation.CLEAN_INSERT, value = "classpath:/UserControllerIntegratedTest/userReport_UsingPagination_PageNotExistent_preparing.xml")
	@DatabaseTearDown(	type = DatabaseOperation.DELETE_ALL, value = "classpath:/UserControllerIntegratedTest/userReport_UsingPagination_PageNotExistent_preparing.xml")
	public void userReport_UsingPagination_PageNotExistent() throws Exception {
		
		mockMvc = MockMvcBuilders.standaloneSetup(userController).setCustomArgumentResolvers(pageableArgumentResolver).build();
		
		resultService = mockMvc.perform(MockMvcRequestBuilders.get("/user/report?page=100"))
		            .andExpect(MockMvcResultMatchers.status().isOk())
		            .andExpect(MockMvcResultMatchers.view().name("user/report"))
		            .andReturn();
		
		assertNotNull(resultService);
		
		Map<String, Object> model = resultService.getModelAndView().getModel();
		assertNotNull(model);
		
		PageWrapper<User> pageWrapper = (PageWrapper<User>)model.get("page");
		assertNotNull(pageWrapper);
		Assert.assertEquals(101, pageWrapper.getNumber());
		Assert.assertEquals(10, pageWrapper.getSize());
		Assert.assertEquals(2, pageWrapper.getTotalPages());
		Assert.assertEquals(0, pageWrapper.getContent().size());
	}



}
