package com.sicpa.thymeleaf.poc.aqualis.controller;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.sicpa.thymeleaf.poc.aqualis.config.TestConfig;
import com.sicpa.thymeleaf.poc.aqualis.enumerator.AuditingOperationType;
import com.sicpa.thymeleaf.poc.aqualis.environment.config.AppConfig;
import com.sicpa.thymeleaf.poc.aqualis.exception.RestResponseEntityExceptionHandler;
import com.sicpa.thymeleaf.poc.aqualis.exception.ServiceException;
import com.sicpa.thymeleaf.poc.aqualis.messages.SystemMessages;
import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.Audit;
import com.sicpa.thymeleaf.poc.aqualis.service.AuditingService;
import com.sicpa.thymeleaf.poc.aqualis.service.PageService;
import com.sicpa.thymeleaf.poc.aqualis.utils.PageWrapper;
import com.sicpa.thymeleaf.poc.aqualis.utils.ProjectMapping;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestConfig.class)
@ActiveProfiles("test")
@WebAppConfiguration
public class AuditingControllerTest {
	
	private MockMvc mockMvc;
	
	@Autowired
	private PageableHandlerMethodArgumentResolver pageableArgumentResolver;
	
	@Autowired
	private AppConfig appConfig;
	
	@InjectMocks
	private AuditingController auditingController;
	
	@Mock
	private AuditingService auditingService;
	
	@Mock
	private PageService pageService;
	
	@Rule
    public ExpectedException thrown = ExpectedException.none();
	
	private MvcResult resultService;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		ReflectionTestUtils.setField(auditingController, "appConfig", appConfig);
		mockMvc = MockMvcBuilders.standaloneSetup(auditingController)
				.setCustomArgumentResolvers(pageableArgumentResolver)
				.setControllerAdvice(new RestResponseEntityExceptionHandler() )
				.build();
	}
	
	@After
	public void tearDown() {
		Mockito.reset(auditingService); 
		Mockito.reset(pageService); 
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testReportSuccess() throws Exception {
		
		Mockito.when(pageService.findAllTitles()).thenReturn(getMockedPageTitles());
		Mockito.when(pageService.findAllActionsByTitle("")).thenReturn(getMockedActions());
		
		Page<Audit> pageAudit = new PageImpl<Audit>(Arrays.asList(new Audit(), new Audit()));
		
		Mockito.when(auditingService.findByPageAndOperationAndUserAndHostnameAndDateBetweenInitialDateAndFinalDate(
				Mockito.isNull(String.class), Mockito.isNull(String.class), Mockito.isNull(AuditingOperationType.class), Mockito.isNull(String.class), 
				Mockito.isNull(String.class), Mockito.isNull(Date.class), Mockito.isNull(Date.class), Mockito.any(PageRequest.class))
			).thenReturn(pageAudit);
		
		resultService = mockMvc.perform(MockMvcRequestBuilders.get("/" + ProjectMapping.AUDIT.BASE_MAPPING + "/" + ProjectMapping.REPORT))
				.andExpect(MockMvcResultMatchers.forwardedUrl(AuditingController.TEMPLATE_REPORT_PAGE))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		
		assertNotNull(resultService);
		
		Map<String, Object> model = resultService.getModelAndView().getModel();
		
		PageWrapper<Audit> pageWrapper = (PageWrapper<Audit>) model.get("page");
		
		assertNotNull(pageWrapper);
		assertNotNull(model);
		assertEquals(3, ((List<String>) model.get("pages")).size());
		assertEquals(2, ((List<String>) model.get("pageActions")).size());
		assertEquals(0, ((Page<Audit>) model.get("pageResult")).getSize());
		assertTrue(pageWrapper.getUrl().contains("/audit/report"));
		
	}
	
	@Test
	public void testReportWithPageTitle() throws Exception {
		
		Mockito.when(pageService.findAllTitles()).thenReturn(getMockedPageTitles());
		Mockito.when(pageService.findAllActionsByTitle("PageTitle")).thenReturn(getMockedActions());
		
		Page<Audit> pageAudit = new PageImpl<Audit>(Arrays.asList(new Audit(), new Audit()));
		
		Mockito.when(auditingService.findByPageAndOperationAndUserAndHostnameAndDateBetweenInitialDateAndFinalDate(
				Mockito.eq("PageTitle"), Mockito.isNull(String.class), Mockito.isNull(AuditingOperationType.class), Mockito.isNull(String.class), 
				Mockito.isNull(String.class), Mockito.isNull(Date.class), Mockito.isNull(Date.class), Mockito.any(PageRequest.class))
			).thenReturn(pageAudit);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/" + ProjectMapping.AUDIT.BASE_MAPPING + "/" + ProjectMapping.REPORT)
				.param("pagetitle", "PageTitle"))
				.andExpect(MockMvcResultMatchers.forwardedUrl(AuditingController.TEMPLATE_REPORT_PAGE))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("pages"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("pageActions"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("pageResult"));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testReportWithNullParams() throws Exception {
		
		Mockito.when(pageService.findAllTitles()).thenReturn(getMockedPageTitles());
		Mockito.when(pageService.findAllActionsByTitle("")).thenReturn(getMockedActions());
		
		Page<Audit> pageAudit = new PageImpl<Audit>(Arrays.asList(new Audit(), new Audit()));
		
		Mockito.when(auditingService.findByPageAndOperationAndUserAndHostnameAndDateBetweenInitialDateAndFinalDate(
				Mockito.isNull(String.class), Mockito.isNull(String.class), Mockito.isNull(AuditingOperationType.class), 
				Mockito.isNull(String.class), Mockito.isNull(String.class), Mockito.isNull(Date.class), Mockito.isNull(Date.class), 
				Mockito.any(PageRequest.class))
			).thenReturn(pageAudit);
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("operationType", null);
		params.add("userinfo", null);
		params.add("hostname", null);
		params.add("pagetitle", null);
		params.add("pageaction", null);
		params.add("initialDate", null);
		params.add("finalDate", null);
		
		resultService = mockMvc.perform(MockMvcRequestBuilders.get("/" + ProjectMapping.AUDIT.BASE_MAPPING + "/" + ProjectMapping.REPORT).params(params))
							.andExpect(MockMvcResultMatchers.forwardedUrl(AuditingController.TEMPLATE_REPORT_PAGE))
							.andExpect(MockMvcResultMatchers.status().isOk())
							.andReturn();

		assertNotNull(resultService);
		
		Map<String, Object> model = resultService.getModelAndView().getModel();
		
		PageWrapper<Audit> pageWrapper = (PageWrapper<Audit>) model.get("page");
		
		assertNotNull(pageWrapper);
		assertNotNull(model);
		assertEquals(3, ((List<String>) model.get("pages")).size());
		assertEquals(2, ((List<String>) model.get("pageActions")).size());
		assertEquals(0, ((Page<Audit>) model.get("pageResult")).getSize());
		assertTrue(pageWrapper.getUrl().contains("/audit/report"));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testReportWithServiceException() throws Exception {
		
		Mockito.when(auditingService.findByPageAndOperationAndUserAndHostnameAndDateBetweenInitialDateAndFinalDate(
				Mockito.isNull(String.class), Mockito.isNull(String.class), Mockito.isNull(AuditingOperationType.class), Mockito.isNull(String.class), 
				Mockito.isNull(String.class), Mockito.isNull(Date.class), Mockito.isNull(Date.class), Mockito.any(PageRequest.class))
			).thenThrow(ServiceException.class);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/" + ProjectMapping.AUDIT.BASE_MAPPING + "/" + ProjectMapping.REPORT))
				.andExpect(MockMvcResultMatchers.forwardedUrl(AuditingController.TEMPLATE_REPORT_PAGE))
				.andExpect(MockMvcResultMatchers.model().attributeExists(SystemMessages.MESSAGE_ATTRIBUTE)).andReturn();
	
	}

	@Test
	public void testGetPageActionsByTitleSuccess() throws Exception {
		
		Mockito.when(pageService.findAllActionsByTitle("ADD")).thenReturn(getMockedActions());

		resultService = mockMvc.perform(MockMvcRequestBuilders.get("/" + ProjectMapping.AUDIT.BASE_MAPPING + "/" + ProjectMapping.AUDIT.PAGE_ACTIONS+"/ADD"))
				.andExpect(MockMvcResultMatchers.status().isOk())
		        .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
				.andReturn();

		assertNotNull(resultService);
		assertNotNull(resultService.getResponse());
		assertEquals("[{\"label\":\"PageActions1\",\"value\":\"PageActions1\"},{\"label\":\"PageActions2\",\"value\":\"PageActions2\"}]", resultService.getResponse().getContentAsString());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetPageActionsByTitleWithServiceException() throws Exception {
		
		Mockito.when(pageService.findAllActionsByTitle("ADD")).thenThrow(ServiceException.class);

		resultService = mockMvc.perform(MockMvcRequestBuilders.get("/" + ProjectMapping.AUDIT.BASE_MAPPING + "/" + ProjectMapping.AUDIT.PAGE_ACTIONS+"/ADD"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		
		assertNotNull(resultService);
		assertNotNull(resultService.getResponse());
		assertEquals("[]", resultService.getResponse().getContentAsString());

	}

	
	private List<String> getMockedActions() {
		List<String> mockedPageActions = new ArrayList<>();
		mockedPageActions.add("PageActions1");
		mockedPageActions.add("PageActions2");
		return mockedPageActions;
	}

	private List<String> getMockedPageTitles() {
		List<String> mockedPageTitles = new ArrayList<>();
		mockedPageTitles.add("Title1");
		mockedPageTitles.add("Title2");
		mockedPageTitles.add("Title3");
		return mockedPageTitles;
	}

}
