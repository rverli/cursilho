package com.sicpa.thymeleaf.poc.aqualis.metadata;

/**
 * @author MJimenez
 * @Description Abstract class of entities' metadata that establishes 
 * a contract to implement by every list view we use
 *
 */
public interface MetaData {
	
	/**
	 * Method that obtains the captions of every column to visualize in the view list
	 * @author MJimenez
	 * @return String[] String array of columns' captions
	 */
	String[] getColumnCaptions();

	/**
	 * Method that obtains the attributes of the entity to use and visualize in the view list
	 * @author MJimenez
	 * @return String[] String array of attributes
	 */
	String[] getColumnAttributes();
	
	/**
	 * Method that obtains the entity attribute that represents the id of the row 
	 * @author MJimenez
	 * @return String entity attribute id
	 */
	String getAttributeId();
	
	/**
	 * Method that obtains the data type of every attribute 
	 * to determine if the entity attribute is boolean 
	 * @author MJimenez
	 * @return boolean[] boolean array of boolean
	 */
	boolean[] getBooleanAttributes();
}
