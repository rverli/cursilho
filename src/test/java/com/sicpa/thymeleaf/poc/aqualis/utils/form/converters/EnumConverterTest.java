package com.sicpa.thymeleaf.poc.aqualis.utils.form.converters;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import com.sicpa.thymeleaf.poc.aqualis.enumerator.AddressType;


public class EnumConverterTest {

	@Test
	public void testSetAsTextString() {
		EnumConverter<AddressType> converter = new EnumConverter<>(AddressType.class);
		converter.setAsText("E");
		Assert.assertEquals(AddressType.E, converter.getValue());
		
		converter.setAsText("S");
		Assert.assertEquals(AddressType.S, converter.getValue());
		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSetAsTextString_Exception(){
		EnumConverter<AddressType> converter = new EnumConverter<>(AddressType.class);
		converter.setAsText("B");
	}
	
	@Test
	public void testSetEmptyText() {
		EnumConverter<AddressType> converter = new EnumConverter<>(AddressType.class);
		converter.setAsText(StringUtils.EMPTY);
		Assert.assertEquals(null, converter.getValue());
	}

	@Test
	public void testSetNullText() {
		EnumConverter<AddressType> converter = new EnumConverter<>(AddressType.class);
		converter.setAsText(null);
		Assert.assertEquals(null, converter.getValue());
	}

}
