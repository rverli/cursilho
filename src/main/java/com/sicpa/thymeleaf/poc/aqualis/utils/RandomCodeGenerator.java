package com.sicpa.thymeleaf.poc.aqualis.utils;

import java.math.BigInteger;

import org.springframework.stereotype.Component;

/**
 * Class responsible to generate Random Codes
 * 
 * @author EBraga
 *
 */
@Component(value = "randomCode")
public class RandomCodeGenerator implements CodeGenerator {
	
	private final LfsrRandom random;
	private final StampCodeEncoder encoder;
	private final StampCodeDecoder decoder;
	
	/**
	 * Creates a new {@link RandomCodeGenerator} for generating a random code in the format XXX-12345
	 */
	public RandomCodeGenerator() {
		this(3, 5);
	}
	
	/**
	 * Creates a new {@link RandomCodeGenerator} for generating a random code in the format XXX{lengthAlphaCode}-12345{lengthNumberCode}
	 * @param lengthAlphaCode
	 * @param lengthNumberCode
	 */
	public RandomCodeGenerator(int lengthAlphaCode, int lengthNumberCode) {
		//Mask for 31,28
		this(lengthAlphaCode, lengthNumberCode, "ABCDEFGHIJKLMNOPQRSTUVWXYZ", "0123456789", new LfsrRandom(BigInteger.valueOf(Long.parseLong("1001000000000000000000000000000", 2)), BigInteger.TEN));
	}
	
	/**
	 * Creates a new {@link RandomCodeGenerator} for generating a random code in the format XXX{lengthAlphaCode}-12345{lengthNumberCode}
	 * @param lengthAlphaCode
	 * @param lengthNumberCode
	 * @param candidateAlpha
	 * @param candidateNumeric
	 * @param random
	 */
	public RandomCodeGenerator(int lengthAlphaCode, int lengthNumberCode, String candidateAlpha, String candidateNumeric, LfsrRandom random) {
		this.random = random;
		this.encoder = new StampCodeEncoder(lengthAlphaCode, lengthNumberCode, candidateAlpha, candidateNumeric);
		this.decoder = new StampCodeDecoder(lengthAlphaCode, lengthNumberCode, candidateAlpha, candidateNumeric);
	}

	/**
	 * Returns the next code.
	 * @return the next code
	 */
	public String generateCode() {
	    return this.encoder.encode((long)(random).next(30));
	}
	
	/**
	 * Returns the code after the given code.
	 * @param lastCode the last code
	 * @return the code after the given code.
	 */
	public String generateCode(String lastCode) {
		setLastCodeGenerated(lastCode);
	    return generateCode();
	}

	@Override
	public void setLastCodeGenerated(String lastCode) {
		long decode = this.decoder.decode(lastCode);
		this.random.setSeed(decode);
	}
	
}

