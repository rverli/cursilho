package com.sicpa.thymeleaf.poc.aqualis.utils;

import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AqualisDateUtilTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testCreateIntIntInt() {
		Date d = AqualisDateUtil.create(2016, Calendar.JANUARY, 1);
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		Assert.assertEquals(1, cal.get(Calendar.DATE));
		Assert.assertEquals(0, cal.get(Calendar.MONTH));
		Assert.assertEquals(2016, cal.get(Calendar.YEAR));
	}

	@Test
	public void testCreateIntIntIntIntIntInt() {
		Date d = AqualisDateUtil.create(2016, Calendar.JANUARY, 1, 23,59,59);
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		Assert.assertEquals(59, cal.get(Calendar.SECOND));
		Assert.assertEquals(59, cal.get(Calendar.MINUTE));
		Assert.assertEquals(23, cal.get(Calendar.HOUR_OF_DAY));
		Assert.assertEquals(1, cal.get(Calendar.DATE));
		Assert.assertEquals(0, cal.get(Calendar.MONTH));
		Assert.assertEquals(2016, cal.get(Calendar.YEAR));

	}

	@Test
	public void testLastDateOfMonth() {
		Assert.assertEquals(29, AqualisDateUtil.lastDateOfMonth(2016, Calendar.FEBRUARY));
		Assert.assertEquals(28, AqualisDateUtil.lastDateOfMonth(2015, Calendar.FEBRUARY));
		Assert.assertEquals(31, AqualisDateUtil.lastDateOfMonth(2016, Calendar.DECEMBER + 1));
	}

}
