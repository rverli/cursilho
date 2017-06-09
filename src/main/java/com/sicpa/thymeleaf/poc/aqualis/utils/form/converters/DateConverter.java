package com.sicpa.thymeleaf.poc.aqualis.utils.form.converters;

import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * Class that convert String to Date
 * @author lrosa1
 *
 * @param <Date>
 */
@SuppressWarnings("unchecked")
public class DateConverter<Date> extends PropertyEditorSupport {
	
	private static final Logger logger = Logger.getLogger(DateConverter.class);

	@Override
    public void setAsText(final String text) {

    	if (StringUtils.isNotBlank(text)) {
    		try {
				DateFormat formatter = new SimpleDateFormat("MM/dd/yy");
				Date date = (Date) formatter.parse(text);
				setValue(date);
			} catch (ParseException e) {
				logger.error(e);
			}
    	}
    }
}
