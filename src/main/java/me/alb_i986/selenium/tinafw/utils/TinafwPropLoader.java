package me.alb_i986.selenium.tinafw.utils;

import me.alb_i986.selenium.tinafw.domain.SupportedBrowser;

import java.net.URL;
import java.util.*;

import org.openqa.selenium.Platform;

/**
 * Static class defining a method for each and every configurable setting
 * (aka property) this framework offers.
 * <p>
 * It is backed by a {@link PropertyLoader}, which hides
 * the low-level details of retrieving properties.
 * This implementation reads properties from the following sources (in the given order):
 * <ol>
 * <li>system properties, defined e.g. as -D arguments to the {@code mvn} command</li>
 * <li>properties files (resources)
 *   <ol>
 *     <li>custom properties file: {@value #CUSTOM_PROPS_RESOURCE}</li>
 *     <li>defaults properties file: {@value #DEFAULT_PROPS_RESOURCE}</li>
 *   </ol>
 * </li>
 * </ol>
 * <p>
 * A property may be:
 * <ul>
 * <li>required: if it is not defined, then throw {@link ConfigException}
 * <li>optional: if it is not defined, then return {@code null}
 * <li>not empty: if it is an empty string, then throw {@link ConfigException}
 * </ul>
 * <p>
 * Clients extending this framework, needing their own configurable settings,
 * should extend this class, and:
 * <ul>
 * <li>define one constant for each new configurable setting, with its name
 *   <ul>
 *     <li>optionally, also redefine (aka hide) the constant {@link #NAMESPACE}
 *         with their own namespace</li>
 *   </ul>
 * </li>
 * <li>define a public static method for each new configurable setting,
 *     implementing the retrieval of that setting, which should delegate to
 *     {@link #getOptionalProperty(String)} or {@link #getRequiredProperty(String)}
 *     </li>
 * <li>optionally, change the logic of how to retrieve properties by redefining
 *     {@link #propLoader}</li>
 * </ul>
 */
public class TinafwPropLoader {

	public static final String NAMESPACE = "tinafw";
	
	protected static final String PROP_PREFIX = NAMESPACE + ".";

	public static final String PROP_BASE_URL = PROP_PREFIX + "base_url";
	public static final String PROP_TIMEOUT_IMPLICIT_WAIT = PROP_PREFIX + "timeout.implicit_wait";
	public static final String PROP_TIMEOUT_EXPLICIT_WAIT = PROP_PREFIX + "timeout.explicit_wait";
	public static final String PROP_KEEP_BROWSERS_OPEN = PROP_PREFIX + "keep_browsers_open";
	public static final String PROP_MAX_EXECUTIONS = PROP_PREFIX + "max_executions";
	public static final String PROP_REPORTS_DIR = PROP_PREFIX + "reports_dir";
	public static final String PROP_BROWSERS = PROP_PREFIX + "browsers";
	public static final String PROP_GRID_PLATFORM = PROP_PREFIX + "grid.platform";
	public static final String PROP_GRID_HUB_URL = PROP_PREFIX + "grid.hub_url";
	public static final String PROP_GRID_BROWSER_VERSION = PROP_PREFIX + "grid.browser_version";
	
	public static final String CUSTOM_PROPS_RESOURCE = "/config.custom.properties";
	public static final String DEFAULT_PROPS_RESOURCE = "/config.default.properties";

	protected static PropertyLoader propLoader = new PropertyLoaderComposite(
				new PropertyLoaderFromSystem(),
				new PropertyLoaderFromResource(CUSTOM_PROPS_RESOURCE, false),
				new PropertyLoaderFromResource(DEFAULT_PROPS_RESOURCE, true)
			);
	
	/**
	 * This is a static class: as such, it cannot be instantiated.
	 */
	protected TinafwPropLoader() {}
	

	/**
	 * @return the property {@value #PROP_BASE_URL}
	 * @throws SettingNotFoundException if the property is not defined
	 */
	public static String getBaseUrl() {
		return getRequiredProperty(PROP_BASE_URL);
	}

	/**
	 * @return the property {@value #PROP_TIMEOUT_IMPLICIT_WAIT};
	 *         null, if not found
	 */
	public static Long getImplicitWait() {
		String value = getOptionalProperty(PROP_TIMEOUT_IMPLICIT_WAIT);
		return (value == null) ? null : Long.parseLong(value);
	}

	/**
	 * @return the property {@value #PROP_TIMEOUT_EXPLICIT_WAIT}
	 * @throws SettingNotFoundException if the property is not defined
	 * 
	 * @see Integer#parseInt(String)
	 */
	public static int getExplicitWait() {
		return Integer.parseInt(getRequiredProperty(PROP_TIMEOUT_EXPLICIT_WAIT));
	}

	/**
	 * @return the property {@value #PROP_KEEP_BROWSERS_OPEN}
	 *         Please note, in case the property is defined and <i>empty</i>,
	 *         it will be considered as {@code false}.
	 * @throws SettingNotFoundException if the property is not defined
	 * 
	 * @see Boolean#parseBoolean(String)
	 */
	public static boolean getKeepBrowsersOpen() {
		return Boolean.parseBoolean(getRequiredProperty(PROP_KEEP_BROWSERS_OPEN));
	}

	/**
	 * @return the property {@value #PROP_MAX_EXECUTIONS}
	 * @throws SettingNotFoundException if the property is not defined
	 * 
	 * @see Integer#parseInt(String)
	 */
	public static int getMaxExecutions() {
		return Integer.parseInt(getRequiredProperty(PROP_MAX_EXECUTIONS));
	}

	/**
	 * @return the property {@value #PROP_REPORTS_DIR}
	 * @throws SettingNotFoundException if the property is not defined
	 */
	public static String getReportsDir() {
		return getRequiredProperty(PROP_REPORTS_DIR);
	}
	
	/**
	 * @return a List of the {@link SupportedBrowser}'s specified in the property
	 *         {@value #PROP_BROWSERS}
	 * 
	 * @throws SettingNotFoundException if the property can't be found,
	 * @throws ConfigException if the property is empty
	 * @throws IllegalArgumentException if one of the values cannot be converted to
	 *         {@link SupportedBrowser}
	 * 
	 * @see PropertiesUtils#split(String)
	 * @see PropertiesUtils#toSupportedBrowsers(String[])
	 */
	public static List<SupportedBrowser> getBrowsers() {
		String[] browserNames = PropertiesUtils.split(getRequiredProperty(PROP_BROWSERS));
		if(browserNames.length == 0)
			throw new ConfigException("no browsers specified in the property " + PROP_BROWSERS);
		return PropertiesUtils.toSupportedBrowsers(browserNames);
	}
	
	/**
	 * @return the {@link Platform} specified in the property {@value #PROP_GRID_PLATFORM};
	 *         null, if not defined
	 */
	public static Platform getGridPlatform() {
		String value = getOptionalProperty(PROP_GRID_PLATFORM);
		return (value == null) ? null : Platform.valueOf(value.toUpperCase());	
	}
	
	/**
	 * @return the property {@value #PROP_GRID_BROWSER_VERSION};
	 *         null, if not defined.
	 */
	public static String getGridBrowserVersion() {
		return getOptionalProperty(PROP_GRID_BROWSER_VERSION);
	}

	/**
	 * @return a {@link URL} with the property {@value #PROP_GRID_HUB_URL};
	 *         null, if not defined.
	 * 
	 * @see PropertiesUtils#toURL(String)
	 */
	public static URL getGridHubUrl() {
		String value = getOptionalProperty(PROP_GRID_HUB_URL);
		return (value == null) ? null : PropertiesUtils.toURL(value);
	}

	
	/**
	 * @param propertyName the name of the property
	 * @return the value of the required property (might be an empty string)
	 * @throws SettingNotFoundException if the property cannot be found
	 */
	protected static String getRequiredProperty(String propertyName) {
		String value = getOptionalProperty(propertyName);
		if(value == null)
			throw new SettingNotFoundException("The property " + propertyName + " is not defined.");
		return value;
	}

	/**
	 * Load the given property from one of the known sources.
	 * <p>
	 * The order of precedence is as follows:
	 * <ol>
	 * <li>system properties, defined e.g. as -D arguments to the {@code mvn} command</li>
	 * <li>custom properties file, {@value #CUSTOM_PROPS_RESOURCE}</li>
	 * <li>defaults properties file, {@value #DEFAULT_PROPS_RESOURCE}</li>
	 * </ol>
	 * 
	 * @param key the name of the property
	 * 
	 * @return the value of the property;
	 *         null if the property is not found
	 */
	protected static String getOptionalProperty(String key) {
		if(!propLoader.isPropertyDefined(key))
			return null;
		return propLoader.getProperty(key).trim();
	}
	
}
