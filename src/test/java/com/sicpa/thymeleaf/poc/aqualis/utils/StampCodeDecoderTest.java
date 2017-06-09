package com.sicpa.thymeleaf.poc.aqualis.utils;

import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * @author Gsalomao
 */
public class StampCodeDecoderTest {

	private StampCodeDecoder decoder = new StampCodeDecoder(); 
	
	@Test(expected=IllegalArgumentException.class)
	public void testDecodeWithInvalidCode() {
		String code = "AAA@0000";
		decoder.decode(code);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testDecodeWithInvalidCodeLength() {
		String code = "AAA0000";
		decoder.decode(code);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testDecodeWithNullCode() {
		String code = null;
		decoder.decode(code);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testDecodeWithEmpty() {
		String code = "    ";
		decoder.decode(code);
	}
	
	@Test
	public void testDecodeZero() {
		String code = "AAA00000";
		long decode = decoder.decode(code);
		Assert.assertEquals(0L, decode);
	}
	
	@Test
	public void testDecodeOne() {
		String code = "AAA00001";
		long decode = decoder.decode(code);
		Assert.assertEquals(1L, decode);
	}
	
	@Test
	public void testDecodeTen() {
		String code = "AAA00010";
		long decode = decoder.decode(code);
		Assert.assertEquals(10L, decode);
	}
	
	@Test
	public void testDecodeAAB00000() {
		String code = "AAB01982";
		long decode = decoder.decode(code);
		Assert.assertEquals(101982L, decode);
	}
	
	@Test
	public void testDecodeAEA00011() {
		String code = "AEA00011";
		long decode = decoder.decode(code);
		Assert.assertEquals(10400011L, decode);
	}
	
	@Test
	public void testDecodeZZZ99999() {
		String code = "ZZZ99999";
		long decode = decoder.decode(code);
		Double number = Math.pow(26, 3) * Math.pow(10, 5) - 1;
		Assert.assertEquals(number.longValue(), decode);
	}
	
	@Test
	public void testDecodeWithCustomNumericConfig(){
		StampCodeDecoder crypto = new StampCodeDecoder(2, 2, "ABCDEFGHIJKLMNOPQRSTUVWXYZ", "9876543210");
		
		String code = "AA00";
		long decode = crypto.decode(code);
		Assert.assertEquals(99L, decode);
	}
	
	@Test
	public void testDecodeWithCustomAlphaAndNumericConfig(){
		StampCodeDecoder crypto = new StampCodeDecoder(2, 2, "GBCDEFAHIJKLMNOPQRSTUVWXYZ", "9876543210");
		
		String code = "AA00";
		long decode = crypto.decode(code);
		Assert.assertEquals(16299L, decode);
	}
}
