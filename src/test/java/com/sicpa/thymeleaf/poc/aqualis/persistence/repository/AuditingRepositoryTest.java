package com.sicpa.thymeleaf.poc.aqualis.persistence.repository;

import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.dao.DataIntegrityViolationException;
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
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.sicpa.thymeleaf.poc.aqualis.config.TestConfig;
import com.sicpa.thymeleaf.poc.aqualis.enumerator.AuditingOperationType;
import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.Audit;
import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.Page;
import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.User;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestConfig.class)
@ActiveProfiles("test")
@WebAppConfiguration
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, 
		DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class, 
		DbUnitTestExecutionListener.class })
public class AuditingRepositoryTest {

	@Autowired
	private AuditingRepository auditingRepository;

	@Test
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "classpath:/AuditingRepositoryTest/save_preparing.xml")
	@DatabaseTearDown(type = DatabaseOperation.DELETE_ALL, value = "classpath:/AuditingRepositoryTest/save_preparing.xml") 
	public void save() {
		long startTime = System.currentTimeMillis();
		
		Assert.assertEquals(0, auditingRepository.count());
		
		User user = new User(2L);
		Page page = new Page(1L);
		Audit audit = new Audit(page, AuditingOperationType.EDIT, user, "localhost", "127.0.0.1");
		audit = auditingRepository.saveAndFlush(audit);
		
		Assert.assertEquals(1, auditingRepository.count());
		Assert.assertNotNull(audit.getId());
		Assert.assertEquals("127.0.0.1", audit.getIp());
		Assert.assertEquals("localhost", audit.getHostName());
		Assert.assertEquals(AuditingOperationType.EDIT, audit.getAuditingOperationType());
		Assert.assertTrue(startTime <= audit.getDateTime().getTime());
		
		Assert.assertNotNull(audit.getUser());
		Assert.assertEquals(2, audit.getUser().getId().intValue());

		Assert.assertNotNull(audit.getPage());
		Assert.assertEquals(1, audit.getPage().getId().intValue());
	}

	@Test(expected=DataIntegrityViolationException.class)
	public void saveNoReferences() {
		Assert.assertEquals(0, auditingRepository.count());
		
		User user = new User(2L);
		Page page = new Page(1L);
		Audit audit = new Audit(page, AuditingOperationType.EDIT, user, "localhost", "127.0.0.1");
		audit = auditingRepository.saveAndFlush(audit);
	}

	@Test
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "classpath:/AuditingRepositoryTest/saveWithNoUser_preparing.xml")
	@DatabaseTearDown(type = DatabaseOperation.DELETE_ALL, value = "classpath:/AuditingRepositoryTest/saveWithNoUser_preparing.xml") 
	public void saveWithNoUser() {
		Assert.assertEquals(0, auditingRepository.count());
		
		Page page = new Page(1L);
		Audit audit = new Audit(page, AuditingOperationType.EDIT, null, "localhost", "127.0.0.1");
		audit = auditingRepository.saveAndFlush(audit);
	}
	
	@Test
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "classpath:/AuditingRepositoryTest/saveWithNoPage_preparing.xml")
	@DatabaseTearDown(type = DatabaseOperation.DELETE_ALL, value = "classpath:/AuditingRepositoryTest/saveWithNoPage_preparing.xml") 
	public void saveWithNoPage() {
		Assert.assertEquals(0, auditingRepository.count());
		
		User user = new User(2L);
		Audit audit = new Audit(null, AuditingOperationType.EDIT, user, "localhost", "127.0.0.1");
		audit = auditingRepository.saveAndFlush(audit);
	}
	
	@Test
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "classpath:/AuditingRepositoryTest/listAudit_preparing.xml")
	@DatabaseTearDown(type = DatabaseOperation.DELETE_ALL, value = "classpath:/AuditingRepositoryTest/listAudit_preparing.xml") 
	public void testFindByPageAndAuditingOperationTypeAndUserAndHostNameAndDateTimeBetween() {
		Date beginDate = buildDate(1, 1, 2016);
		Date endDate = buildDate(10, 1, 2016);

		org.springframework.data.domain.Page<Audit> audits = 
				auditingRepository.findByPageAndAuditingOperationTypeAndUserAndHostNameAndDateTimeBetween(
						"user", "hostname1", "Edição de Usuário", "Editar Usuário", AuditingOperationType.EDIT, beginDate, endDate, null);
		
		Assert.assertNotNull( audits );
		Assert.assertFalse( audits.getContent().isEmpty() );
		Assert.assertEquals(2, audits.getContent().size());
	}	
	
	private Date buildDate(int day, int month, int year) {
		Calendar date = Calendar.getInstance();
		date.set(Calendar.DAY_OF_MONTH, day);
		date.set(Calendar.MONTH, month);
		date.set(Calendar.YEAR, year);
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);
		return date.getTime();
	}

}
