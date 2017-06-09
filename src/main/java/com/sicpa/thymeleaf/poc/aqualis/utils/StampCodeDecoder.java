package com.sicpa.thymeleaf.poc.aqualis.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Performs decode of the stamp code.
 * @author Gsalomao
 */
public class StampCodeDecoder {

	private final String[] patternAsArray;
	
	public StampCodeDecoder() {
		this(3, 5, "ABCDEFGHIJKLMNOPQRSTUVWXYZ", "0123456789");
	}
	
	public StampCodeDecoder(int alphaLength, int numericLenght, String alphaCandidate, String numericCandidate) {
		this(StampCodeUtil.getPatternAsArray(alphaLength, numericLenght, alphaCandidate, numericCandidate));
	}
	
	public StampCodeDecoder(String[] patternAsArray) {
		this.patternAsArray = patternAsArray;
	}
	
	/**
	 * Returns the number behind the code
	 * @param code the code to be decoded
	 * @return the number behind the code
	 */
	public long decode(final String code) {
		int codeLenght = patternAsArray.length;
		if(code != null && code.length() != codeLenght)
			throw new IllegalArgumentException("Code \""+code+"\" has unexpected length, expected: "+codeLenght);
		
		return getSeed(code);
	}
	
	/**
	 * Returns the seed of code
	 * @param code the code 
	 * @return the seed of code
	 */
	private Long getSeed(final String code) {
		if(code == null || code.length() == 0)
			throw new IllegalArgumentException("Invalid Code: "+ code);
		
		Integer[] baseArray = new Integer[code.length()];
		
		Double result = 0.0;
		List<String> patternsAsList = Arrays.asList(patternAsArray);
		Collections.reverse(patternsAsList);
		
		String reverseCode = new StringBuilder(code).reverse().toString();
		for(int i=0; i<code.length(); i++) {
			char currentChar = reverseCode.charAt(i);
			
			String candidate = patternsAsList.get(i);
			int charIndex = candidate.indexOf(currentChar);
			int base = candidate.length();
			if(charIndex < 0 ) {
				throw new IllegalArgumentException("Invalid Code: "+ code);
			}
			
			result = result + getCurrentValue(charIndex, baseArray);
			
			baseArray[i] = base;
		}
		return result.longValue();
	}

	/**
	 * Returns the value of current number considering the previous numbers base.
	 * 
	 * @param value the current number
	 * @param baseArray the previous numbers bases
	 * @return the current value
	 */
	private Double getCurrentValue(int value, Integer[] baseArray) {
		Double result = new Double(value);
		for(Integer currentBase: baseArray) {
			if(currentBase == null)
				break;
			
			result = result * currentBase;  
		}
		
		return result;
	}
	
}
