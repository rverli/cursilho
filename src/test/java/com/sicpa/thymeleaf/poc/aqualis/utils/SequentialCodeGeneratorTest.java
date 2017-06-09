package com.sicpa.thymeleaf.poc.aqualis.utils;

import org.junit.Assert;
import org.junit.Test;

public class SequentialCodeGeneratorTest {
	
	private SequentialCodeGenerator generator;
	
	@Test
	public void testGenerateNumberWithValidPattern() {
		generator = new SequentialCodeGenerator();
		generator.setLastCodeGenerated("AA000000001");
		Assert.assertEquals("AA000000002", generator.generateCode());
	}

	@Test
	public void testGenerateNumberWithSecondCharStartWithZCharacterAndRangeLimitExceeded() {
		generator = new SequentialCodeGenerator();
		generator.setLastCodeGenerated("AZ999999999");
		Assert.assertEquals("BA000000001", generator.generateCode());
	}

	@Test
	public void testGenerateNumberWithFirstCharStartWithZCharacterAndRangeLimitExceeded() {
		generator = new SequentialCodeGenerator();
		generator.setLastCodeGenerated("ZA999999999");
		Assert.assertEquals("ZB000000001", generator.generateCode());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGenerateNumberWithInvalidPattern() {
		generator = new SequentialCodeGenerator();
		generator.setLastCodeGenerated("ZA9999999999");
		generator.generateCode();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGenerateNumberWithNullArgument() {
		generator = new SequentialCodeGenerator();
		generator.setLastCodeGenerated(null);
		generator.generateCode();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGenerateNumberWithEmptyArgument() {
		generator = new SequentialCodeGenerator();
		generator.setLastCodeGenerated("");
		generator.generateCode();
	}
	
	@Test
	public void testGenerateNumberWithLowerCaseLetters() {
		generator = new SequentialCodeGenerator();
		generator.setLastCodeGenerated("aa000000001");
		Assert.assertEquals("AA000000002", generator.generateCode());		
	}
	
	@Test
	public void testGenerateNumberStartWithZZCharactersAndRangeLimitExceeded() {
		generator = new SequentialCodeGenerator();
		generator.setLastCodeGenerated("ZZ999999999");
		Assert.assertEquals("AA000000001", generator.generateCode());				
	}
}
