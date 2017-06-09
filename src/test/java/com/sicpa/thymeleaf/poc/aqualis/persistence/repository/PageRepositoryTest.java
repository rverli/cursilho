package com.sicpa.thymeleaf.poc.aqualis.persistence.repository;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.sicpa.thymeleaf.poc.aqualis.config.TestConfig;
import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.Page;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestConfig.class)
@ActiveProfiles("test")
@WebAppConfiguration
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class })
public class PageRepositoryTest{

	@Autowired
	private PageRepository pageRepository; 
	
	@Test
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "classpath:/PageRepositoryTest/testFindPageByPath_preparing.xml")
	public void testFindByPath() {
		String path        = null;
		Page page = pageRepository.findByPath(path);
		Assert.assertNull( page );
		
		path = "user/edit";
		page = pageRepository.findByPath(path);
		Assert.assertNotNull( page );
	}
	
	@Test
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "classpath:/PageRepositoryTest/pagedefault_preparing.xml")
	public void testFindAllActionsByTitle() {
		List<String> actions = pageRepository.findAllActionsByTitle("User Admin");
		Assert.assertNotNull(actions);
		Assert.assertEquals(3, actions.size());
	}

	@Test
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "classpath:/PageRepositoryTest/pagedefault_preparing.xml")
	public void testFindAllTitles() {
		List<String> actions = pageRepository.findAllTitles();
		Assert.assertNotNull(actions);
		Assert.assertEquals(2, actions.size());
	}
}
