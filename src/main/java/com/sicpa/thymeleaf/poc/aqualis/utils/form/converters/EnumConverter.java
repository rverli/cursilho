package com.sicpa.thymeleaf.poc.aqualis.utils.form.converters;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang3.StringUtils;

/**
 * Class that convert String to Enum
 * @author lrosa1
 *
 * @param <T>
 */
public class EnumConverter<T extends Enum<T>> extends PropertyEditorSupport {

    private static final String OPTION_NOT_SELECTED = "-1";
	private final Class<T> typeParameterClass;

    public EnumConverter(Class<T> typeParameterClass) {
        super();
        this.typeParameterClass = typeParameterClass;
    }

    @Override
    public void setAsText(final String text) {
    	if (StringUtils.isNotBlank(text) && !text.equals(OPTION_NOT_SELECTED)) {
	        String upper = text.toUpperCase(); // or something more robust
	        T value = T.valueOf(typeParameterClass, upper);
	        setValue(value);
    	}
    }
    
}
