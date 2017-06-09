package com.sicpa.thymeleaf.poc.aqualis.utils.form.validators.phone;

import javax.validation.ConstraintValidatorContext;

import org.junit.Assert;
import org.junit.Test;

public class PhoneNumberValidatorTest {

	@Test
	public void testIsValid() {
		PhoneNumberValidator validator = new PhoneNumberValidator();
		
		ConstraintValidatorContext ctx = null;
		String phoneNumber = null;
		Assert.assertFalse( validator.isValid(phoneNumber, ctx));
		
		phoneNumber = "";
		Assert.assertFalse( validator.isValid(phoneNumber, ctx));
		
		phoneNumber = "123";
		Assert.assertFalse( validator.isValid(phoneNumber, ctx));
		
		phoneNumber = "(21) 1452-3214";
		Assert.assertFalse( validator.isValid(phoneNumber, ctx));

		phoneNumber = "(21) 91452-3214";
		Assert.assertFalse( validator.isValid(phoneNumber, ctx));

		phoneNumber = "(01) 3452-3214";
		Assert.assertFalse( validator.isValid(phoneNumber, ctx));

		phoneNumber = "(21) 3452-3214";
		Assert.assertTrue( validator.isValid(phoneNumber, ctx));

		phoneNumber = "(21) 98452-3214";
		Assert.assertTrue( validator.isValid(phoneNumber, ctx));
		
	}

}
