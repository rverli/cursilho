package com.sicpa.thymeleaf.poc.aqualis.controller.integrated.audting;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.sicpa.thymeleaf.poc.aqualis.config.TestConfig;
import com.sicpa.thymeleaf.poc.aqualis.controller.AuditingController;
import com.sicpa.thymeleaf.poc.aqualis.exception.RestResponseEntityExceptionHandler;
import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.Audit;
import com.sicpa.thymeleaf.poc.aqualis.utils.PageWrapper;
import com.sicpa.thymeleaf.poc.aqualis.utils.ProjectMapping;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestConfig.class)
@ActiveProfiles("test")
@WebAppConfiguration
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, 
	DirtiesContextTestExecutionListener.class,
	TransactionalTestExecutionListener.class, 
	DbUnitTestExecutionListener.class,
	WithSecurityContextTestExecutionListener.class})
public class AuditingControllerIntegratedTest {

	private MockMvc mockMvc;
	
	private MvcResult resultService;

	@Autowired
	private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

	@Autowired
	private AuditingController auditingController;
	
	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(auditingController)
				.setCustomArgumentResolvers(pageableArgumentResolver)
				.setControllerAdvice(new RestResponseEntityExceptionHandler() )
				.build();
	}
	

	@SuppressWarnings("unchecked")
	@Test
	@DatabaseSetup(	type = DatabaseOperation.CLEAN_INSERT, value = "classpath:/AuditingControllerIntegrated/report_preparing.xml")
	@DatabaseTearDown(	type = DatabaseOperation.DELETE_ALL, value = "classpath:/AuditingControllerIntegrated/report_preparing.xml")
	public void testReport() throws Exception {
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("userinfo", "user");
		params.add("pagetitle", "Edicao de Usuario");
		params.add("pageaction", "Editar Usuario");
		params.add("operation", "ADD");
		params.add("initialDate", "2016-05-01");
		params.add("finalDate", "2016-05-02");
		
		resultService = mockMvc.perform(MockMvcRequestBuilders.get("/" + ProjectMapping.AUDIT.BASE_MAPPING + "/" + ProjectMapping.REPORT)
								.params(params))
								.andExpect(MockMvcResultMatchers.forwardedUrl(AuditingController.TEMPLATE_REPORT_PAGE))
								.andExpect(MockMvcResultMatchers.model().attributeExists("pages"))
								.andExpect(MockMvcResultMatchers.model().attributeExists("pageActions"))
								.andExpect(MockMvcResultMatchers.model().attributeExists("pageResult"))
								.andExpect(MockMvcResultMatchers.model().attributeExists("page"))
								.andReturn();
		
		Map<String, Object> model = resultService.getModelAndView().getModel();
		
		PageWrapper<Audit> pageWrapper = (PageWrapper<Audit>) model.get("page");
		assertEquals(2, ((List<String>) model.get("pages")).size());
		assertEquals(1, ((List<String>) model.get("pageActions")).size());
		assertEquals(10, ((org.springframework.data.domain.Page<Audit>) model.get("pageResult")).getSize());
		assertTrue(pageWrapper.getUrl().contains("/audit/report"));
	}

	@Test
	@DatabaseSetup(	type = DatabaseOperation.CLEAN_INSERT, value = "classpath:/AuditingControllerIntegrated/report_preparing.xml")
	@DatabaseTearDown(	type = DatabaseOperation.DELETE_ALL, value = "classpath:/AuditingControllerIntegrated/report_preparing.xml")
	public void testGetPageActionsByTitle() throws Exception {
		
		resultService = mockMvc.perform(MockMvcRequestBuilders.get("/" + ProjectMapping.AUDIT.BASE_MAPPING + "/" + ProjectMapping.AUDIT.PAGE_ACTIONS +"/Edicao de Usuario"))
				.andExpect(MockMvcResultMatchers.status().isOk())
		        .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
				.andReturn();

		assertNotNull(resultService);
		assertNotNull(resultService.getResponse());
		assertEquals("[{\"label\":\"Editar Usuario\",\"value\":\"Editar Usuario\"}]", resultService.getResponse().getContentAsString());
		
	}

}
