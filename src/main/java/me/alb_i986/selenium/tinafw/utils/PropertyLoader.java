package me.alb_i986.selenium.tinafw.utils;

/**
 * Abstract class capable of reading properties from a certain source.
 * <p>
 * Each concrete implementor should be focused on a specific source.
 * Sources may be for example files, or environment variables, DBs, and so on.
 */
public abstract class PropertyLoader {

	/**
	 * @param key the name of the property
	 * 
	 * @return the value of the given property;
	 *         null if the property is not defined
	 */
	public abstract String getProperty(String key);
	
	/**
	 * @return true if the given property is defined in the source.
	 */
	public boolean isPropertyDefined(String key) {
		return getProperty(key) != null;
	}

	/**
	 * @return true if the given property is not defined or it is an empty string
	 */
	public boolean isPropertyEmpty(String key) {
		if(!isPropertyDefined(key))
			return true;
		return getProperty(key).isEmpty();
	}

}
