package com.sicpa.thymeleaf.poc.aqualis.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.MissingResourceException;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class StringUtilsTest {
	
	@Rule
    public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testEncodeStringUTF8() {
		try {
			assertEquals("", StringUtils.encodeStringUTF8(null));
			assertEquals("%26", StringUtils.encodeStringUTF8("&"));
		} catch (UnsupportedEncodingException e) {
			fail(e.getMessage());
		}
	}

	@Test(expected = Exception.class)
	public void testPrivateConstructor() throws Exception {
		Constructor<StringUtils> constructor = StringUtils.class.getDeclaredConstructor();
		Assert.assertTrue(Modifier.isPrivate(constructor.getModifiers()));
		constructor.setAccessible(true);
		constructor.newInstance();
	}
	
	@Test
	public void getStringFromMessagesSuccess(){
		assertEquals("Aqualis", StringUtils.getStringFromMessages("app.web.project.name"));
	}
	
	@Test
	public void getStringFromMessagesWithEmptyName(){
		thrown.expect(MissingResourceException.class);
        thrown.expectMessage("Can't find resource for bundle java.util.PropertyResourceBundle, key");
        StringUtils.getStringFromMessages("");
	}
	
	@Test
	public void getStringFromMessagesWithNullName(){
        assertEquals("",StringUtils.getStringFromMessages(null));
	}
	
}
