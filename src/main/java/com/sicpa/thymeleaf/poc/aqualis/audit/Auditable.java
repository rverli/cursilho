package com.sicpa.thymeleaf.poc.aqualis.audit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.sicpa.thymeleaf.poc.aqualis.enumerator.AuditingOperationType;

/**
 * Interface to provide audit.
 *  
 * @author Gsalomao
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface Auditable {
	/**
	 * the action performed. Used only for audit purposes.
	 * @return the action performed
	 */
	AuditingOperationType operationType();
}
