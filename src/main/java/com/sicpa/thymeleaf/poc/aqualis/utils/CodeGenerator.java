package com.sicpa.thymeleaf.poc.aqualis.utils;

/**
 * 
 * Responsible for generating a code
 *
 */
public interface CodeGenerator {
	
	/**
	 * Method responsible for generating a code
	 * @return the code generated
	 */
	String generateCode();
	
	/**
	 * Some implementations needed a information about last code generated to generated the next
	 * @param lastCode
	 */
	void setLastCodeGenerated(String lastCode);

}
