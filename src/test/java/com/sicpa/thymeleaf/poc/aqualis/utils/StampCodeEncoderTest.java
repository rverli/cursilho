package com.sicpa.thymeleaf.poc.aqualis.utils;

import java.math.BigInteger;

import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * @author Gsalomao
 */
public class StampCodeEncoderTest {

	private StampCodeEncoder encoder = new StampCodeEncoder(); 
	
	@Test(expected=IllegalArgumentException.class)
	public void testEncodeInvalidLength() {
		Double number = Math.pow(26, 3) * Math.pow(10, 5);
		encoder.encode(number.longValue());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testEncodeNull() {
		encoder.encode(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testEncodeNegative() {
		encoder.encode(-1L);
	}
	
	@Test
	public void testEncodeZero() {
		String expectedCode = "AAA00000";
		String code = encoder.encode(BigInteger.ZERO.longValue());
		Assert.assertEquals(expectedCode, code);
	}
	
	@Test
	public void testEncodeOne() {
		String expectedCode = "AAA00001";
		String code = encoder.encode(BigInteger.ONE.longValue());
		Assert.assertEquals(expectedCode, code);
	}
	
	@Test
	public void testEncodeTen() {
		String expectedCode = "AAA00010";
		String code = encoder.encode(BigInteger.TEN.longValue());
		Assert.assertEquals(expectedCode, code);
	}
	
	@Test
	public void testEncode101982() {
		String expectedCode = "AAB01982";
		String code = encoder.encode(101982L);
		Assert.assertEquals(expectedCode, code);
	}
	
	@Test
	public void testEncode10400011() {
		String expectedCode = "AEA00011";
		String code = encoder.encode(10400011L);
		Assert.assertEquals(expectedCode, code);
	}

	@Test
	public void testDecodeZZZ99999() {
		String expectedCode = "ZZZ99999";
		Double number = Math.pow(26, 3) * Math.pow(10, 5) - 1;
		String code = encoder.encode(number.longValue());
		Assert.assertEquals(expectedCode, code);
	}
	
	@Test
	public void testEncodeWithCustomNumerciConfig(){
		StampCodeEncoder encoder = new StampCodeEncoder(2, 2, "ABCDEFGHIJKLMNOPQRSTUVWXYZ", "9876543210");
		
		String expectedCode = "AA00";
		String code = encoder.encode(99L);
		Assert.assertEquals(expectedCode, code);
	}
	
	@Test
	public void testEncodeWithCustomAlphaAndNumericConfig(){
		StampCodeEncoder encoder = new StampCodeEncoder(2, 2, "GBCDEFAHIJKLMNOPQRSTUVWXYZ", "9876543210");
		
		String expectedCode = "AA00";
		String code = encoder.encode(16299L);
		Assert.assertEquals(expectedCode, code);
	}
}
