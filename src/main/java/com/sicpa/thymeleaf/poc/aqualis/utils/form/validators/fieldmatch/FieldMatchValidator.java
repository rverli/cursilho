package com.sicpa.thymeleaf.poc.aqualis.utils.form.validators.fieldmatch;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

/**
 * Validation class to FieldMatch Annotation
 * 
 * @author lrosa1
 *
 */
public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {

	private String firstFieldName;
	private String secondFieldName;

	private static final Logger logger = Logger.getLogger(FieldMatchValidator.class);

	@Override
	public void initialize(final FieldMatch constraintAnnotation) {
		firstFieldName = constraintAnnotation.first();
		secondFieldName = constraintAnnotation.second();
	}

	@Override
	public boolean isValid(final Object value, final ConstraintValidatorContext context) {
		try {
			final Object firstObj = BeanUtils.getProperty(value, firstFieldName);
			final Object secondObj = BeanUtils.getProperty(value, secondFieldName);
			
			if (firstObj == null || secondObj == null) {
				return false;
			}
			
			return firstObj.equals(secondObj);
			
		} catch (final Exception ex) {
			logger.fatal(ex);
		}
		return true;
	}
}
