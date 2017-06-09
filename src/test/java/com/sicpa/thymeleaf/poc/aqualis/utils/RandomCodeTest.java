package com.sicpa.thymeleaf.poc.aqualis.utils;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RandomCodeTest {

	private RandomCodeGenerator generator;
	
	@Before
	public void setUp(){
		generator = new RandomCodeGenerator();
	}
	
	@Test
	public void testGenerateStampWithFiveNumbersAndThreeLetters() {
		String stampExpected = generator.generateCode();
		
		Assert.assertNotNull(stampExpected);
		Assert.assertEquals(8, stampExpected.length());
		Assert.assertTrue( StringUtils.isAlpha( stampExpected.substring(0, 2) ));
		Assert.assertTrue( StringUtils.isNumeric( stampExpected.substring(3, 7) ));
	}

	
	@Test
	public void testGenerateStampAndRegenerateFromPreviousStamp() {
		String firstStamp = generator.generateCode();
		
		Assert.assertNotNull(firstStamp);
		Assert.assertEquals(8, firstStamp.length());
		Assert.assertTrue( StringUtils.isAlpha( firstStamp.substring(0, 2) ));
		Assert.assertTrue( StringUtils.isNumeric( firstStamp.substring(3, 7) ));
		
		String secondStamp = generator.generateCode();
		
		Assert.assertNotNull(secondStamp);
		Assert.assertEquals(8, secondStamp.length());
		Assert.assertTrue( StringUtils.isAlpha( secondStamp.substring(0, 2) ));
		Assert.assertTrue( StringUtils.isNumeric( secondStamp.substring(3, 7) ));
		
		String secondStampRegenerade = generator.generateCode(firstStamp);
		
		Assert.assertEquals(secondStamp, secondStampRegenerade);
	}
	
}
