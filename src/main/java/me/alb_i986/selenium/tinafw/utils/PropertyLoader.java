package me.alb_i986.selenium.tinafw.utils;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Loads config properties from the known sources, which are:
 * <ul>
 * <li>command line arguments</li>
 * <li>properties files ({@value #DEFAULT_PROPS_RESOURCE} and {@value #CUSTOM_PROPS_RESOURCE})</li>
 * </ul>
 */
public class PropertyLoader {

	public static final String CUSTOM_PROPS_RESOURCE = "/selenium-tinafw.custom.properties";
	public static final String DEFAULT_PROPS_RESOURCE = "/selenium-tinafw.default.properties";
	
	private static final Logger logger = Logger.getLogger(PropertyLoader.class);

	private static Properties customProps = new Properties();
	private static Properties defaultProps = new Properties();

	static {
		// first thing first, let's read the files and load the Properties objects
		try {
			defaultProps.load(PropertyLoader.class.getResourceAsStream(DEFAULT_PROPS_RESOURCE));
		} catch (IOException e) {
			logger.error("Cannot load properties from " + DEFAULT_PROPS_RESOURCE, e);
		}
		try {
			InputStream customPropsResource = PropertyLoader.class.getResourceAsStream(CUSTOM_PROPS_RESOURCE);
			// if the custom file does not exist, never mind, go on!
			if(customPropsResource == null) {
				logger.warn("Skip loading custom props: " + CUSTOM_PROPS_RESOURCE + " cannot be found.");
			} else {
				customProps.load(customPropsResource);
			}
		} catch (IOException e) {
			logger.warn("Cannot load properties from " + CUSTOM_PROPS_RESOURCE, e);
		}
	}
	
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
	 * @throws ConfigNotFoundException if the config cannot be found
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
		throw new ConfigNotFoundException();
	}

}