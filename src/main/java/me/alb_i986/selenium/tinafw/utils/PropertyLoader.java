package me.alb_i986.selenium.tinafw.utils;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Read config properties from the known sources, which are:
 * <ul>
 * <li>command line arguments</li>
 * <li>properties files ({@value #DEFAULT_PROPS_RESOURCE} and {@value #CUSTOM_PROPS_RESOURCE})</li>
 * </ul>
 */
public class PropertyLoader {

	public static final String CUSTOM_PROPS_RESOURCE = "/selenium-tinafw.custom.properties";
	public static final String DEFAULT_PROPS_RESOURCE = "/selenium-tinafw.default.properties";
	
	private static final Logger logger = Logger.getLogger(PropertyLoader.class);

	private static Properties customProps = loadPropsFromResource(CUSTOM_PROPS_RESOURCE, false);
	private static Properties defaultProps = loadPropsFromResource(DEFAULT_PROPS_RESOURCE, true);

	/**
	 * This is a static class: as such, it cannot be instantiated.
	 */
	private PropertyLoader() {}
	
	/**
	 * Convenience method for getting a config in the namespace "tinafw".
	 * The prefix "tinafw." is prepended for you.
	 * 
	 * @param name the name of the wanted config without the prefix "tinafw."
	 * @see #getConfig(String)
	 */
	public static String getTinaFwConfig(String name) {
		return getConfig("tinafw." + name);
	}
	
	/**
	 * Load the given config value from one of the known sources.
	 * The order of precedence is as follows:
	 * <ol>
	 * <li>command line arguments</li>
	 * <li>custom properties files: {@value #CUSTOM_PROPS_RESOURCE}</li>
	 * <li>defaults properties files: {@value #DEFAULT_PROPS_RESOURCE}</li>
	 * </ol>
	 * 
	 * @param name the name of the wanted config (aka the key)
	 * @return the value of the wanted config
	 * @throws ConfigException if the config cannot be found
	 */
	public static String getConfig(String name) {
		String propFromCmdLine = System.getProperty(name);
		if(propFromCmdLine != null)
			return propFromCmdLine;
		String customPropFromFile = customProps.getProperty(name);
		if(customPropFromFile != null)
			return customPropFromFile;
		String defaultPropFromFile = defaultProps.getProperty(name);
		if(defaultPropFromFile != null)
			return defaultPropFromFile;
		throw new ConfigException("The property " + name +
				" is not defined in any known sources.");
	}
	
	/**
	 * Load properties from a resource.
	 * @param resourceName
	 * @param failOnResourceNotFound when true,
	 *        a ConfigException is raised if the resource cannot be found
	 * @return a new Properties object
	 * @throws ConfigException if the resource cannot be found, and failOnResourceNotFound is true
	 */
	private static Properties loadPropsFromResource(String resourceName, boolean failOnResourceNotFound) {
		Properties props = new Properties();
		InputStream resource = PropertyLoader.class.getResourceAsStream(resourceName);
		if(resource == null) {
			if(failOnResourceNotFound) {
				throw new ConfigException("Config file " + resourceName + " not found");
			} else { // if the file does not exist, return an empty Properties
				logger.warn("Skipping resource " + resourceName + ": file not found.");
				return props;
			}
		}
		try {
			props.load(resource);
		} catch (IOException e) {
			throw new ConfigException("Cannot read properties from " + resourceName, e);
		}
		return props;
	}

}
