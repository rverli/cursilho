package com.sicpa.thymeleaf.poc.aqualis.messages;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

public class ObjectMapperUtilTest {

	@Test
	public void testConvertJsonMessage() throws JsonProcessingException {
		String message = "message";
		Assert.assertEquals("{\"message\":\"" + message + "\"}", ObjectMapperUtil.convertJsonMessage(message ));
		
		Assert.assertEquals("{\"message\":null}", ObjectMapperUtil.convertJsonMessage( null ));
	}

}
