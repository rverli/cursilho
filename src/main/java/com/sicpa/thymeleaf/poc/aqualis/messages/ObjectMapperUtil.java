package com.sicpa.thymeleaf.poc.aqualis.messages;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperUtil {
	
	private ObjectMapperUtil() {}
	
	public static String convertJsonMessage(String message) throws JsonProcessingException {
		
		Map<String, String> resultMap = new HashMap<>();
		resultMap.put("message", message);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(resultMap);
	}
}
