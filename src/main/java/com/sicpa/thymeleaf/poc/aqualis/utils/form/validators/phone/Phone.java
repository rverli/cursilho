package com.sicpa.thymeleaf.poc.aqualis.utils.form.validators.phone;

import java.lang.annotation.*;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = PhoneNumberValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Phone {

    String message() default "{Phone}";
    
    Class<?>[] groups() default {};
      
    Class<? extends Payload>[] payload() default {};
	
}
