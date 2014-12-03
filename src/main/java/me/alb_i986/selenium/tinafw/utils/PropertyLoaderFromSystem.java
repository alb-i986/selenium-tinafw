package me.alb_i986.selenium.tinafw.utils;

/**
 * Read properties from the system.
 * <p>
 * Such properties can be defined e.g. as -D arguments to the {@code mvn} command.
 * 
 * @see System#getProperties()
 */
public class PropertyLoaderFromSystem extends PropertyLoader {

	/**
	 * @see System#getProperty(String)
	 */
	@Override
	public String getProperty(String key) {
		return System.getProperty(key);
	}
	
}
