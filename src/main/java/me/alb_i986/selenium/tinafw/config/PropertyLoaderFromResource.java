package me.alb_i986.selenium.tinafw.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Read properties from a resource file.
 */
public class PropertyLoaderFromResource extends PropertyLoader {

	private static final Logger logger = LogManager.getLogger(PropertyLoaderFromResource.class);

	private Properties propsFromResource;

	public PropertyLoaderFromResource(String resourceName, boolean isResourceRequired) {
		this.propsFromResource = loadPropsFromResource(resourceName, isResourceRequired);
	}

	@Override
	public String getProperty(String key) {
		return propsFromResource.getProperty(key);
	}
	
	/**
	 * Load properties from a resource.
	 * 
	 * @param resourceName
	 * @param failOnResourceNotFoundOrNotLoaded when true, a ConfigException
	 *        is raised if the resource cannot be found or loaded
	 * @return a {@link Properties} instance with the properties loaded from the resource;
	 *         might be empty if the resource is not found or if it cannot be loaded
	 * @throws ConfigException if the resource cannot be found or loaded,
	 *         and failOnResourceNotFound is true
	 *         
	 * @see Properties#load(InputStream)
	 */
	private static Properties loadPropsFromResource(String resourceName, boolean failOnResourceNotFoundOrNotLoaded) {
		Properties props = new Properties();
		InputStream resource = PropertyLoaderFromResource.class.getResourceAsStream(resourceName);
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
				logger.warn("Cannot load properties from " + resourceName + ": " + e.getMessage());
		}
		return props;
	}

}
