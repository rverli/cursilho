package com.sicpa.thymeleaf.poc.aqualis.persistence.entity;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.sicpa.thymeleaf.poc.aqualis.enumerator.ProfileType;

public class UserTest {

	private User user;
	@Before
	public void setUp() throws Exception {
		user = new User();
	}

	@Test
	public void testHasRole() {
		Assert.assertFalse( user.hasProfile(null) );
		
		Assert.assertFalse( user.hasProfile(ProfileType.PROFILE_ADMIN) );
		
		List<Profile> profiles = new ArrayList<>();
		user.setProfiles(profiles);
		
		Assert.assertFalse( user.hasProfile(ProfileType.PROFILE_ADMIN) );
		
		Profile profileUser = new Profile();
		profiles.add( profileUser );
		
		Assert.assertFalse( user.hasProfile(ProfileType.PROFILE_ADMIN) );
		profileUser.setName("PROFILE_USER");
		Assert.assertFalse( user.hasProfile(ProfileType.PROFILE_ADMIN) );
		
		
		Profile profileAdmin = new Profile();
		profileAdmin.setName("PROFILE_ADMIN");
		profiles.add( profileAdmin );
		
		Assert.assertTrue( user.hasProfile(ProfileType.PROFILE_ADMIN) );
	}

	@Test
	public void testEqualsObject() {

		Assert.assertTrue( user.equals(user) );
		
		Assert.assertFalse( user.equals(null) );
		
		Assert.assertFalse( user.equals(new String()) );
		
		User userTest = new User();
		Assert.assertTrue( user.equals(userTest) );
		
		userTest.setId(1L);
		Assert.assertFalse( user.equals(userTest) );
		
		user.setId(1L);
		Assert.assertTrue( user.equals(userTest) );
		Assert.assertTrue( userTest.equals(user) );
		
		
		userTest.setId(2L);
		Assert.assertFalse( user.equals(userTest) );
		

	}

}
