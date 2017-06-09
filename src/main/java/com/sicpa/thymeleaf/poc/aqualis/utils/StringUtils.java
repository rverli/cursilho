package com.sicpa.thymeleaf.poc.aqualis.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * String utility class for operations not found in StringUtils from commons library 
 * </p>
 * @author ekoetsier
 *
 */
public class StringUtils {
	
	private static Logger logger = LoggerFactory.getLogger(StringUtils.class);
	
	private StringUtils() {
		throw new UnsupportedOperationException();
	}

	/**
	 * <p>
	 * Encodes a string to UTF-8. Returns an empty string if parameter is null
	 * </p>
	 * @param s the String to be encoded
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String encodeStringUTF8(String s) throws UnsupportedEncodingException {
		return s!= null ? URLEncoder.encode(s, StandardCharsets.UTF_8.name()) : "";
	}	
	
	public static String getStringFromMessages(String messageName){
		String result = "";
		
		if (messageName != null){
			try {
				ResourceBundle resourceBundle = ResourceBundle.getBundle("messages");
				result = resourceBundle.getString(messageName);
			} catch (MissingResourceException e) {
				logger.error("Propriedade ["+messageName+"] não encontrada", e);
				throw e;
			} 
		}
		return result;
		
	}
	
	/**
	 * <p>
	 * Decode a string to UTF-8. Returns an empty string if parameter is null
	 * </p>
	 * @param s the String to be decoded
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String decodeStringUTF8(String s) throws UnsupportedEncodingException {
		return s!= null ? URLDecoder.decode(s, StandardCharsets.UTF_8.name()) : "";
	}
}
