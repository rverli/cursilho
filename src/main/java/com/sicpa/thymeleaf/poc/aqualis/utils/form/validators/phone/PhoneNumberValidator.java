package com.sicpa.thymeleaf.poc.aqualis.utils.form.validators.phone;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<Phone, String> {

	@Override
	public void initialize(Phone paramA) {
		//Not necessary since phonenumber is passed to the isValid method
	}

	@Override
	public boolean isValid(String phoneNumber, ConstraintValidatorContext ctx) {
		if (phoneNumber == null) {
			return false;
		}
				
		return phoneNumber.matches(".((10)|([1-9][1-9]).)\\s9?[6-9][0-9]{3}-[0-9]{4}") || 
				phoneNumber.matches(".((10)|([1-9][1-9]).)\\s[2-5][0-9]{3}-[0-9]{4}");
	}

}
