package com.sicpa.thymeleaf.poc.aqualis.utils;

/**
 * Utility class for stamp code generation 
 * @author Gsalomao
 *
 */
public class StampCodeUtil {

	/**
	 * Returns an array to represents the code pattern. Concatenates alpha before numerics candidates using the length parameters to performs repetitions of candidates.   
	 * @param alphaLength number of repetitions of alpha candidates 
	 * @param numericLenght number of repetitions of numeric candidates
	 * @param alphaCandidate a string to represents the alpha possibilities in desired order.
	 * @param numericCandidate a string to represents the numeric possibilities in desired order.
	 * @return An array to represents the code pattern
	 */
	public static final String[] getPatternAsArray(int alphaLength, int numericLenght, String alphaCandidate, String numericCandidate) {
		String[] patternAsArray = new String[alphaLength + numericLenght];
		for (int i = 0; i<alphaLength; i++){
			patternAsArray[i] = alphaCandidate;
		}
		for (int i = alphaLength; i<alphaLength+numericLenght; i++){
			patternAsArray[i] = numericCandidate;
		}
		return patternAsArray;
	}
}
