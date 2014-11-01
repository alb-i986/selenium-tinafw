package me.alb_i986.selenium.tinafw.utils;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Read properties from the known sources, i.e.:
 * <ul>
 * <li>command line arguments</li>
 * <li>properties files
 *   <ul>
 *     <li>default properties file</li>
 *     <li>custom properties file</li>
 *   </ul>
 * </li>
 * </ul>
 */
public class PropertyLoader {

	private static final Logger logger = Logger.getLogger(PropertyLoader.class);

	private Properties customProps;
	private Properties defaultProps;

	public PropertyLoader(String defaultPropsResource, String customPropsResource) {
		defaultProps = loadPropsFromResource(defaultPropsResource, true);
		customProps = loadPropsFromResource(customPropsResource, false);
	}


	/**
	 * @param namespace e.g. "my.namespace" (with no starting/ending periods)
	 * @param propName the name of the property, after the namespace
	 * @return the value of the required property
	 * 
	 * @see #getProperty(String)
	 */
	public String getProperty(String namespace, String propName) {
		return getProperty(namespace + "." + propName);
	}
	
	/**
	 * Load the given property from one of the known sources.
	 * The order of precedence is as follows:
	 * <ol>
	 * <li>command line arguments</li>
	 * <li>custom properties file</li>
	 * <li>defaults properties file</li>
	 * </ol>
	 * 
	 * @param key the name of the wanted property, <b>namespace included</b>
	 * @return the value of the wanted property
	 * @throws ConfigException if the property cannot be found
	 */
	public String getProperty(String key) {
		String propFromCmdLine = System.getProperty(key);
		if(propFromCmdLine != null)
			return propFromCmdLine.trim();
		String customPropFromFile = customProps.getProperty(key);
		if(customPropFromFile != null)
			return customPropFromFile.trim();
		String defaultPropFromFile = defaultProps.getProperty(key);
		if(defaultPropFromFile != null)
			return defaultPropFromFile.trim();
		throw new ConfigException("The property " + key +
				" is not defined in any known sources.");
	}
	
	/**
	 * Load properties from a resource.
	 * 
	 * @param resourceName
	 * @param failOnResourceNotFoundOrNotLoaded when true, a ConfigException
	 *        is raised if the resource cannot be found or loaded
	 * @return a {@link Properties} with the properties loaded from the resource
	 * @throws ConfigException if the resource cannot be found or loaded,
	 *         and failOnResourceNotFound is true
	 */
	private Properties loadPropsFromResource(String resourceName, boolean failOnResourceNotFoundOrNotLoaded) {
		Properties props = new Properties();
		InputStream resource = PropertyLoader.class.getResourceAsStream(resourceName);
		boolean resourceNotFound = (resource == null);
		if(resourceNotFound) {
			if(failOnResourceNotFoundOrNotLoaded) {
				throw new ConfigException("resource " + resourceName + " not found");
			} else {
				// if the resource is not found, return an empty Properties
				logger.warn("Skipping resource " + resourceName + ": file not found.");
				return props;
			}
		}
		try {
			props.load(resource);
		} catch (IOException e) {
			if(failOnResourceNotFoundOrNotLoaded)
				throw new ConfigException("Cannot load properties from " + resourceName, e);
			else
				logger.warn("Cannot load properties from " + resourceName + ". " + e.getMessage());
		}
		return props;
	}

}
