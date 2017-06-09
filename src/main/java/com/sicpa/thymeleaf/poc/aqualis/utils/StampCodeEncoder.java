package com.sicpa.thymeleaf.poc.aqualis.utils;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Performs encode for stamp code.
 * @author Gsalomao
 */
public class StampCodeEncoder {

	private final List<String> reversePatternAsArray;
	
	public StampCodeEncoder() {
		this(3, 5, "ABCDEFGHIJKLMNOPQRSTUVWXYZ", "0123456789");
	}
	
	public StampCodeEncoder(int alphaLength, int numericLenght, String alphaCandidate, String numericCandidate) {
		this(StampCodeUtil.getPatternAsArray(alphaLength, numericLenght, alphaCandidate, numericCandidate));
	}

	public StampCodeEncoder(String[] patternAsArray) {
		this.reversePatternAsArray = Arrays.asList(patternAsArray);
		Collections.reverse(reversePatternAsArray);
	}

	/**
	 * Returns the code by given value.
	 * @param value the value to be encoded.
	 * @return the code by given value
	 */
	public String encode(Long value) {
		if(value == null || value < 0) 
			throw new IllegalArgumentException("Only non negative numbers are allowed. Value: "+value);
		
		Long limit = getLimit();
		if(value > limit)
			throw new IllegalArgumentException("This value \""+value+"\" is bigger than expected \""+limit+"\"");
		
		StringBuilder result = new StringBuilder();
		BigInteger quotient = BigInteger.valueOf(value);
		int rest = 0;
		for(int i=0; i<reversePatternAsArray.size(); i++) {
			String base = reversePatternAsArray.get(i);

			BigInteger currentBase = BigInteger.valueOf(base.length());
			
			BigInteger[] divideAndRemainder = quotient.divideAndRemainder(currentBase);
			quotient = divideAndRemainder[0];
			rest = divideAndRemainder[1].intValue();
			
			result.append(base.charAt(rest)); 
		}
				
		return result.reverse().toString();
	}
	
	/**
	 * Returns the maximum value by the given code pattern.
	 * @return the maximum value by the given code pattern
	 */
	private Long getLimit(){
		Long limit = 1L;
		for(String pattern: reversePatternAsArray) 
			limit *= pattern.length();
		
		return limit -1;
	}
}
