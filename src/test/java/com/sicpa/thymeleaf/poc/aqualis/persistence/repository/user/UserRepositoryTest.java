package com.sicpa.thymeleaf.poc.aqualis.persistence.repository.user;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.TransactionSystemException;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.sicpa.thymeleaf.poc.aqualis.config.TestConfig;
import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.Profile;
import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.User;
import com.sicpa.thymeleaf.poc.aqualis.persistence.repository.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestConfig.class)
@ActiveProfiles("test")
@WebAppConfiguration
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class })
@Ignore
public class UserRepositoryTest {

	
	
	@Autowired
	UserRepository userRepository;

	@Test
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "classpath:/UserRepositoryTest/findAllUsers_preparing.xml")
	public void findAllUsers() {
		List<User> result = (List<User>) userRepository.findAll();
		Assert.assertTrue(CollectionUtils.isNotEmpty(result));
		Assert.assertEquals(2, result.size());
		Assert.assertEquals("user 01", result.get(0).getName());
	}

	@Test
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "classpath:/UserRepositoryTest/removeUsers_preparing.xml")
	@ExpectedDatabase(assertionMode=DatabaseAssertionMode.NON_STRICT, value="classpath:/UserRepositoryTest/removeUsers_assert.xml")
	public void removeUsers() {
		userRepository.delete(new User(2l));
	}

	@Test
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "classpath:/UserRepositoryTest/editUser_preparing.xml")
	public void editUser() {
		User user = userRepository.findOne(1l);
		Assert.assertEquals("user 01", user.getName());
		user.setName("edited name");
		user.setPassword("123");
		user.setConfirmPassword("123");
		userRepository.saveAndFlush(user);
		user = userRepository.findOne(1l);
		Assert.assertEquals("edited name", user.getName());
	}
	
	@Test
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "classpath:/UserRepositoryTest/newUser_preparing.xml")
	public void newUser() {
		User user = new User();
		user.setName("name");
		user.setEmail("mail@mail.com");
		user.setPhoneNumber("(21) 98756-8888");
		user.setPassword("32323232");
		user.setConfirmPassword("32323232");
		userRepository.save(user);
		user = userRepository.findOne(user.getId());
		Assert.assertNotNull(user);
		Assert.assertEquals("mail@mail.com", user.getEmail());		
	}

	@Test(expected = TransactionSystemException.class)
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "classpath:/UserRepositoryTest/newUserWithInvalidCPF_preparing.xml")
	public void newUserWithInvalidCPF() {
		User user = new User();
		user.setName("name");
		user.setEmail("mail@mail.com");
		user.setPhoneNumber("(21) 98756-8888");
		user.setPassword("32323232");
		userRepository.save(user);		
	}

	@Test(expected = TransactionSystemException.class)
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "classpath:/UserRepositoryTest/newUserWithInvalidMail_preparing.xml")	
	public void newUserWithInvalidMail() {
		User user = new User();
		user.setName("name");
		user.setEmail("invalid mail");
		user.setPhoneNumber("(21) 98756-8888");
		user.setPassword("32323232");
		userRepository.save(user);		
	}
	
	@Test
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "classpath:/UserRepositoryTest/findAllUsers_preparing.xml")	
	public void findUserByEmailContaining() {
		String email      = null;
		Pageable pageable = new PageRequest(0, 10);
		Page<User> page   = userRepository.findUserByEmailContaining(email, pageable );
		Assert.assertNotNull( page );
		Assert.assertEquals(2, page.getContent().size() );
	}
	
	@Test
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "classpath:/UserRepositoryTest/findAllUsers_preparing.xml")	
	public void findUserByEmail() {
		String email      = null;
		User user         = userRepository.findUserByEmail(email);
		Assert.assertNull( user );
				
		email = "mail1@mail.com";
		user  = userRepository.findUserByEmail(email);
		Assert.assertNotNull( user );
	}
	
	
	
	
	@Test
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "classpath:/UserRepositoryTest/findAllUsers_preparing.xml")	
	public void findByNameAndEmailAndCpfAndActiveContaining() {
		String email      = null;
		String name       = null;
		Boolean active    = null;
		Pageable pageable = new PageRequest(0, 10);
		Page<User> page   = userRepository.findByNameAndEmailAndActiveContaining(name, email, active, pageable);
		Assert.assertNotNull( page );
		Assert.assertEquals(2,  page.getTotalElements() );
				
		email = "mail1@mail.com";
		page   = userRepository.findByNameAndEmailAndActiveContaining(name, email, active, pageable);
		Assert.assertNotNull( page );
		Assert.assertEquals(1,  page.getTotalElements() );
	}

	@Test
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "classpath:/UserRepositoryTest/findByProfilesIn_preparing.xml")		
	public void testFindByProfilesInWithValidRoles() {
		List<Profile> profiles = new ArrayList<>();
		profiles.add(new Profile(3l));
		profiles.add(new Profile(5l));
		
		List<User> users = userRepository.findByProfilesIn(profiles);
		
		Assert.assertNotNull(users);
		Assert.assertEquals(2, users.size());
	}
		
	@Test
	@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "classpath:/UserRepositoryTest/findByProfilesIn_preparing.xml")		
	public void testFindByProfilesInWithNotExistingRoles() {
		List<Profile> profiles = new ArrayList<>();
		profiles.add(new Profile(10l));
		profiles.add(new Profile(15l));
		
		List<User> users = userRepository.findByProfilesIn(profiles);
		
		Assert.assertEquals(0, users.size());
		
	}
}
