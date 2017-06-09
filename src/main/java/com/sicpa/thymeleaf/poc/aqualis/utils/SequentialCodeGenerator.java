package com.sicpa.thymeleaf.poc.aqualis.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hsqldb.lib.StringUtil;
import org.springframework.stereotype.Component;

/**
 * Utility class to generate a incremented sequential number based in last number recorded. 
 * @author lrosa1
 *
 */
@Component(value = "sequentialCode")
public class SequentialCodeGenerator implements CodeGenerator {
	
	private static final String SEQUENTIAL_CODE_PATTERN = "[A-Z]{%d}[0-9]{%d}";
	private static final long RANGE_LIMIT = 999999999l;
	private static final int ALPHA = 2;
	private static final int DIGITS = 9;
	private static final String DIGIT_FORMAT = "%09d";
	private static final Pattern PATTERN = Pattern.compile(String.format(SEQUENTIAL_CODE_PATTERN, ALPHA, DIGITS)); 
	
	private String lastStamp;
	
	/**
	 * Default constructor 
	 */
	public SequentialCodeGenerator() {
		//default constructor
	}
	
	/**
	 * Method to generate a incremented sequential number based in last number recorded.
	 * Repeatedly calling this method will generate new codes based on the last code generated
	 * 
	 * @return a string in pattern XX123456789
	 */
	public String generateCode() {
		
		if (StringUtil.isEmpty(lastStamp)) {
			throw new IllegalArgumentException("LastStamp is Null or empty");
		}
		
		lastStamp = lastStamp.toUpperCase();
		
		validateSequentialCodePattern(this.lastStamp);

		//extract the parts
		String letters = lastStamp.substring(0, ALPHA);
		String stringNumber = lastStamp.substring(ALPHA);
		
		//increment the number
		long longNumber = Long.parseLong(stringNumber) + 1;
		
		//increment the letters if number exceeds the limit
		if (longNumber > RANGE_LIMIT) {
			letters = incrementLetters(letters);
			longNumber = 1; //restart the sequence
		}
		
		//generate a sequential number
		lastStamp = letters + String.format(DIGIT_FORMAT, longNumber);

		return lastStamp;
	}
	
	private void validateSequentialCodePattern(String lastStamp) {
		Matcher matcher = PATTERN.matcher(lastStamp);
		if (!matcher.matches()) {
			throw new IllegalArgumentException("The stamp pattern is not valid.");
		}
	}

	private String incrementLetters(String letters) {
		
		char letterOne = letters.charAt(0);
		char letterTwo = letters.charAt(1);
		
		if ("ZZ".equalsIgnoreCase(letters)) {
			letterOne = 'A';
			letterTwo = 'A';
			
		} else {
			if (letterTwo < 'Z') {
				letterTwo += 1;
				
			} else if (letterTwo == 'Z' && letterOne < 'Z') {
				letterOne += 1;
				letterTwo = 'A';
			}
		}
		return String.valueOf(letterOne) + String.valueOf(letterTwo);
	}

	@Override
	public void setLastCodeGenerated(String lastCode) {
		this.lastStamp = lastCode;
	}
	
}

