package com.sicpa.thymeleaf.poc.aqualis.metadata;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sicpa.thymeleaf.poc.aqualis.environment.config.AppConfig;
import com.sicpa.thymeleaf.poc.aqualis.exception.MetadataException;

/**
 * Class that implements the abstract MetaData class using reflection 
 * to get dynamically the attributes of the entities and load 
 * the configuration of the config.properties file to create in execution time 
 * the table view according the configuration established in the configuration file. 
 * @author MJimenez
 *
 * @param <T> Any entity class that can be used to get a table view
 */
public class MetaEntity<T> implements MetaData{
	
	private static final Log logger = LogFactory.getLog(MetaEntity.class);
	
	private static final String PREFIX_KEY_WEB_METADATA 		 	= "app.web.metadata";
	private static final String KEY_WEB_METADATA_CONSTRUCTOR_ERROR 	= "app.web.metadata.constructor.error";
	private static final String KEY_WEB_METADATA_ATTRIBUTE_MISSING 	= "app.web.metadata.attribute.missing=";
	private static final String KEY_WEB_METADATA_ATTRIBUTE_NEEDED	= "app.web.metadata.attribute.needed";
	private static final String KEY_WEB_METADATA_ATTRIBUTE_ERROR 	= "app.web.metadata.view.attribute.error";
	private static final String KEY_WEB_METADATA_ATTRIBUTE_ID_MISSING = "app.web.metadata.attribute.id.not.founded";
	private static final String KEY_WEB_METADATA_MISSING            = "app.web.metadata.not.founded";
	private static final String POINT_SEPARATOR 				 	= ".";
	private static final String COMMA_SPACE_SEPARATOR 			 	= ", ";
	private static final String VIEW_SUFIX 						 	= "view";
	private static final String CAPTION_SUFIX 					 	= "caption";
	private static final String FORMAT_SUFIX 					 	= "format";
	private static final String ATTRIBUTE_ID_SUFIX 				 	= "attribute.id";
	private static final String DEFAULT_DECIMAL_FORMAT = PREFIX_KEY_WEB_METADATA + ".default.decimal.format";
	private static final String DEFAULT_INTEGER_FORMAT = PREFIX_KEY_WEB_METADATA + ".default.integer.format";
	private static final String DEFAULT_DATETIME_FORMAT = PREFIX_KEY_WEB_METADATA + ".default.dateTime.format";
	
	private AppConfig appConfig;
	
	private String attributeId;
	private String entityName;
	private T entity;
	private String[] columnAttributes;
	private String[] columnCaptions;
	private boolean[] booleanAttributes;
	private boolean[] textAttributes;
	private boolean[] integerAttributes;
	private boolean[] decimalAttributes;
	private boolean[] dateAttributes;
	private boolean[] enumAttributes;
	private String[] formats;
		
	/**
	 * <p>
	 * Constructor for the class creates teh metadata 
	 * </p>
	 * @param cClass
	 * @param appConfig
	 * @throws MetadataException
	 */
	public MetaEntity(Class<T> cClass, AppConfig appConfig) throws MetadataException{
    	try {
			this.entity = cClass.newInstance();				
			this.appConfig = appConfig;
			getMetaEntityData();
		} catch (MetadataException e) {
			throw e;
		} catch (Exception e) {
			throw new MetadataException(appConfig.getProperty(KEY_WEB_METADATA_CONSTRUCTOR_ERROR), e);
		}
	}
	
	private void loadAttributeFormat(String attribute, int index, String defaultFormat) {
		String strPrefixKeyEntityProperty = PREFIX_KEY_WEB_METADATA + POINT_SEPARATOR + this.entity.getClass().getSimpleName().toLowerCase();
		String fullPropertyName = strPrefixKeyEntityProperty + POINT_SEPARATOR + CAPTION_SUFIX + POINT_SEPARATOR + attribute + POINT_SEPARATOR + FORMAT_SUFIX;
		
		if (appConfig.getProperty(fullPropertyName) != null){
			this.formats[index] = appConfig.getProperty(fullPropertyName, "entity." + attribute);
		} else {
			if (defaultFormat != null) {
				this.formats[index] = appConfig.getProperty(defaultFormat, "entity." + attribute);
			} else {
				this.formats[index] = "entity." + attribute;
			}
		}
	}
	
	/**
	 * private method that loads in the constructor the properties 
	 * from configfile and using reflection to get the attributes 
	 * and key properties from the file.
	 */
	private void getMetaEntityData() throws MetadataException{
		//get the name of the entity
		String strNameEntity = this.entity.getClass().getSimpleName();
		this.entityName = strNameEntity.substring(0, 1).toLowerCase() + strNameEntity.substring(1);
		
		//establishing the prefix key used for all properties of the entity
		String strPrefixKeyEntityProperty = PREFIX_KEY_WEB_METADATA + POINT_SEPARATOR + strNameEntity.toLowerCase();
		
		//getting the column attributes from the properties
		String strColumnAttributes = appConfig.getProperty(strPrefixKeyEntityProperty + POINT_SEPARATOR + VIEW_SUFIX);
		if (strColumnAttributes==null){
			throw new MetadataException(appConfig.getProperty(KEY_WEB_METADATA_MISSING) + " " + strPrefixKeyEntityProperty + POINT_SEPARATOR + VIEW_SUFIX);
		}
		this.columnAttributes = strColumnAttributes.split(",");
		
		//validation of existence of column attribute
		if (this.columnAttributes.length < 1){
			throw new MetadataException(appConfig.getProperty(KEY_WEB_METADATA_ATTRIBUTE_MISSING));
		}
		
		//getting the column captions for the entity view 
		readColumnCaptions(strPrefixKeyEntityProperty);
		
		//getting the attribute id
		this.attributeId = appConfig.getProperty(strPrefixKeyEntityProperty + POINT_SEPARATOR + ATTRIBUTE_ID_SUFIX);
		if (this.attributeId == null){
			throw new MetadataException(appConfig.getProperty(KEY_WEB_METADATA_ATTRIBUTE_ID_MISSING));
		}
		
		//get the columns and data type used in the view
		Map<String, String> fieldMap = getAttributesUsedInView();
	    
	    //getting boolean, integer, decimal, date and text attributes
	    this.booleanAttributes = new boolean[this.columnAttributes.length];
	    this.textAttributes = new boolean[this.columnAttributes.length];
	    this.integerAttributes = new boolean[this.columnAttributes.length];
	    this.decimalAttributes = new boolean[this.columnAttributes.length];
	    this.dateAttributes = new boolean[this.columnAttributes.length];
	    this.enumAttributes = new boolean[this.columnAttributes.length];
	    this.formats = new String[this.columnAttributes.length];
	    
	    for(int i=0;i<this.columnAttributes.length;i++){
	    	String defaultFormat = null;
	    	switch (fieldMap.get(this.columnAttributes[i]).toLowerCase()) {
		    	case "boolean":
		    		this.booleanAttributes[i] = true;
		    		break;
		    	case "string":
		    		this.textAttributes[i] = true;
		    		break;
		    	case "enum":
		    		this.enumAttributes[i] = true;
		    		break;
		    	case "integer":
		    		this.integerAttributes[i] = true;
		    		defaultFormat = DEFAULT_INTEGER_FORMAT;
		    		break;
		    	case "number":
		    		this.decimalAttributes[i] = true;
		    		defaultFormat = DEFAULT_DECIMAL_FORMAT;
		    		break;
		    	case "date":
		    		this.dateAttributes[i] = true;
		    		defaultFormat = DEFAULT_DATETIME_FORMAT;
		    		break;
		    	default:
	    		
	    	}
	    	loadAttributeFormat(this.columnAttributes[i], i, defaultFormat);
	    }
	}

	private void readColumnCaptions(String strPrefixKeyEntityProperty) throws MetadataException {
		StringBuilder strAttNeeded = new StringBuilder("");
		this.columnCaptions = new String[this.columnAttributes.length];
		for(int i=0;i<this.columnAttributes.length;i++){
			if (appConfig.getProperty(strPrefixKeyEntityProperty + POINT_SEPARATOR + CAPTION_SUFIX + POINT_SEPARATOR + this.columnAttributes[i]) == null){
				throw new MetadataException(appConfig.getProperty(KEY_WEB_METADATA_MISSING) + " " + strPrefixKeyEntityProperty + POINT_SEPARATOR + CAPTION_SUFIX + POINT_SEPARATOR + this.columnAttributes[i]);
			}
			this.columnCaptions[i] = appConfig.getProperty(strPrefixKeyEntityProperty + POINT_SEPARATOR + CAPTION_SUFIX + POINT_SEPARATOR + this.columnAttributes[i]);
			if (this.columnCaptions[i] == null){
				if (strAttNeeded.length() > 0){
					strAttNeeded.append(COMMA_SPACE_SEPARATOR);
				}
				strAttNeeded.append(strPrefixKeyEntityProperty + POINT_SEPARATOR + CAPTION_SUFIX + POINT_SEPARATOR + this.columnAttributes[i]);
			}
		}
		//validation of existence of all caption attributes needed
		if (strAttNeeded.length() > 0){
			throw new MetadataException(appConfig.getProperty(KEY_WEB_METADATA_ATTRIBUTE_NEEDED) + " " + strAttNeeded);
		}
	}

	@SuppressWarnings("rawtypes")
	private Map<String, String> getAttributesUsedInView() throws MetadataException {
		Map<String, String> fieldMap = new HashMap<>();
		try {
			Class c = this.entity.getClass();
			
			for(String columnName : this.columnAttributes) {
				includeAttribute(fieldMap, c, columnName);
			}
		} catch (Exception e) {
			throw new MetadataException(appConfig.getProperty(KEY_WEB_METADATA_ATTRIBUTE_ERROR), e);
		}
		
		return fieldMap;
	}

	@SuppressWarnings("rawtypes")
	private void includeAttribute(Map<String, String> fieldMap, Class c, String columnName) {
		try {
			if( columnName.indexOf(POINT_SEPARATOR) < 0 ){
				Field field = c.getDeclaredField(columnName);
				if (boolean.class.equals(field.getType())){
					fieldMap.put(columnName, Boolean.class.getSimpleName());					
				}else if (double.class.equals(field.getType()) || float.class.equals(field.getType()) || Number.class.equals(field.getType())
						|| Double.class.equals(field.getType()) || Float.class.equals(field.getType())){
					fieldMap.put(columnName, Number.class.getSimpleName());
				}else if (Integer.class.equals(field.getType()) || int.class.equals(field.getType()) || long.class.equals(field.getType()) 
						|| short.class.equals(field.getType()) || Long.class.equals(field.getType()) || Short.class.equals(field.getType())){
					fieldMap.put(columnName, Integer.class.getSimpleName());
				}else if (Date.class.equals(field.getType())){
					fieldMap.put(columnName, Date.class.getSimpleName());
				}else if (((Class<?>)field.getType()).isEnum()){
					fieldMap.put(columnName, Enum.class.getSimpleName());
				}else{
					fieldMap.put(columnName, String.class.getSimpleName());
				}
			}else{
				fieldMap.put(columnName, String.class.getSimpleName());	
			}
		} catch (NoSuchFieldException e) {
			logger.error("Field not found: "+columnName, e);
		}
	}

	public T getEntity() {
	    return entity;
	}
	
	/**
	  * {@inheritDoc}
	  */
	@Override
	public String[] getColumnCaptions() {
		return this.columnCaptions;
	}

	/**
	  * {@inheritDoc}
	  */
	@Override
	public String[] getColumnAttributes() {
		return this.columnAttributes;
	}
	
	/**
	  * {@inheritDoc}
	  */
	@Override
	public boolean[] getBooleanAttributes(){
		return this.booleanAttributes;
	}
	
	/**
	  * {@inheritDoc}
	  */
	@Override
	public String getAttributeId() {
		return attributeId;
	}

	public boolean[] getTextAttributes() {
		return textAttributes;
	}

	public boolean[] getIntegerAttributes() {
		return integerAttributes;
	}

	public boolean[] getDecimalAttributes() {
		return decimalAttributes;
	}

	public boolean[] getDateAttributes() {
		return dateAttributes;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public boolean[] getEnumAttributes() {
		return enumAttributes;
	}

	public String[] getFormats() {
		return formats;
	}
	
}
