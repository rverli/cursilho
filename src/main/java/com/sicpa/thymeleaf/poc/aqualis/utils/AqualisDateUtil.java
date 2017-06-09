package com.sicpa.thymeleaf.poc.aqualis.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.Validate;

import com.sicpa.thymeleaf.poc.aqualis.messages.SystemMessages;

/**
 * Responsible for realizing operations for {@link Date}
 * @author ekoetsier
 *
 */
public final class AqualisDateUtil {
	
private  static List<String> listMonths = new ArrayList<>();
	
	private AqualisDateUtil() {}
	
	
	static{
		for(int i=1;i<=12;i++){
			listMonths.add(Integer.toString(i));
		}
	}


	/**
	 * 
	 * Create a {@link Date} based on year, month and day
	 * 
	 * @param year year for the date 
	 * @param month month for the date
	 * @param date day for the month
	 * @return
	 */
	public static Date create(int year, int month, int date){
		return AqualisDateUtil.create(year, month, date, 0,0,0);
	}


	/**
	 * Creates a Date based on the parameters passed
	 * @param year
	 * @param month
	 * @param date
	 * @param hour
	 * @param minutes
	 * @param seconds
	 * @return
	 */
	public static Date create(int year, int month, int date, int hour, int minutes, int seconds) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, date, hour, minutes, seconds);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}


	/**
	 * 
	 *  Determines the last date of the month
	 * 
	 * @param year
	 * @param month
	 * @return last date of the month
	 */
	public static int lastDateOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month + 1, 1);
		cal.add(Calendar.DATE, -1);
		return cal.get(Calendar.DATE);
	}
	
	/**
	 * List all months of the year
	 * 
	 * @param year
	 * @return
	 */
	public static List<String> listMonths(Integer year) {
		if (year!=null && year>0){
			return listMonths;
		}
		return Collections.emptyList();
	}
	
	/**
	 * Converts a Date to String with a {@link DataFormatType} predefined
	 * @param date
	 * @param format
	 * @return
	 */
	public static String getStringDate(Date date, DataFormatType format){
		DateFormat df = new SimpleDateFormat(format.getLabel());
		return df.format(date);
	}
	
	/**
	 * Enum that secures the data type formats used to convert a date to String
	 * @author mjimenez
	 *
	 */
	public static enum DataFormatType {
		FORMAT_YYYY_MM_DD("yyyy-MM-dd"),
		FORMAT_DD_MM_YYYY("dd/MM/yyyy");
		
		private String label;
		
		private DataFormatType(String label) {
			this.label = label;
		}
		
		public String getLabel() {
			return label;
		}
		
		@Override
		public String toString() {
			return label;
		}
	}

	/**
	 * add a number of milliseconds to a date 
	 * @param date the date to add the milliseconds 
	 * @param timeValid milliseconds to add to the date
	 * @return
	 */
	public static Date add(Date date, long timeValid) {
		Validate.notNull(date, SystemMessages.INVALID_PARAMETER);
		return new Date(date.getTime() + timeValid);
	}

}
