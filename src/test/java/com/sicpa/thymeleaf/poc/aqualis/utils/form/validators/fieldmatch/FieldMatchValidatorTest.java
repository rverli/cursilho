package com.sicpa.thymeleaf.poc.aqualis.utils.form.validators.fieldmatch;

import javax.validation.ConstraintValidatorContext;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.User;
import com.sicpa.thymeleaf.poc.aqualis.utils.form.validators.fieldmatch.FieldMatch;
import com.sicpa.thymeleaf.poc.aqualis.utils.form.validators.fieldmatch.FieldMatchValidator;

@RunWith(MockitoJUnitRunner.class)
public class FieldMatchValidatorTest {
	
	@InjectMocks
	private FieldMatchValidator fieldMatchValidator;
	
	@Mock
	private FieldMatch constraintAnnotation;
	
	private User user;
	
	@Before
	public void setup() {
		user = new User();
	}
	
	@Test
	public void testIsValid() {
		
		FieldMatchValidator fieldMatchValidator = new FieldMatchValidator();
		ConstraintValidatorContext ctx = null;

		Mockito.when(constraintAnnotation.first()).thenReturn("password");
		Mockito.when(constraintAnnotation.second()).thenReturn("confirmPassword");
		fieldMatchValidator.initialize(constraintAnnotation);
		
		user.setPassword(null);
		user.setConfirmPassword(null);
		Assert.assertFalse(fieldMatchValidator.isValid(user, ctx));
		
		user.setPassword("123");
		user.setConfirmPassword(null);
		Assert.assertFalse(fieldMatchValidator.isValid(user, ctx));

		user.setPassword("123");
		user.setConfirmPassword("vfdddd");
		Assert.assertFalse(fieldMatchValidator.isValid(user, ctx));
		
		user.setPassword("123");
		user.setConfirmPassword("123");
		Assert.assertTrue(fieldMatchValidator.isValid(user, ctx));
	}
		
}
