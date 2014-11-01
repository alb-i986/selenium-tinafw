package me.alb_i986.selenium.tinafw.utils;

import me.alb_i986.selenium.tinafw.domain.SupportedBrowser;

import java.net.URL;
import java.util.*;

import org.openqa.selenium.Platform;

/**
 * Static class defining a method for each and every property
 * the system requires, with namespace {@value #NAMESPACE}.
 * <p>
 * It is backed by a {@link PropertyLoader}, which hides
 * the low-level details of retrieving properties.
 * <p>
 * Clients extending this framework, needing custom config properties
 * should extend this class, and:
 * <ul>
 * <li>redefine (aka hide) the constant {@link #NAMESPACE} with their own</li>
 * <li>define a private static method that uses {@link #propLoader}
 *     (just like {@link #getTinaFwConfig(String)})</li>
 * <li>define a public static method for each custom property,
 *     which delegate to the private method</li>
 * <li>should NOT redefine {@link #CUSTOM_PROPS_RESOURCE}
 *     and {@link #DEFAULT_PROPS_RESOURCE}</li>
 * </ul>
 * 
 * @see PropertyLoader
 */
public class TinafwPropLoader {

	public static final String NAMESPACE = "tinafw";
	public static final String CUSTOM_PROPS_RESOURCE = "/config.custom.properties";
	public static final String DEFAULT_PROPS_RESOURCE = "/config.default.properties";

	protected static final PropertyLoader propLoader =
			new PropertyLoader(DEFAULT_PROPS_RESOURCE, CUSTOM_PROPS_RESOURCE);
	
	/**
	 * This is a static class: as such, it cannot be instantiated.
	 */
	protected TinafwPropLoader() {}
	

	/**
	 * @return the property "tinafw.base_url"
	 */
	public static String getBaseUrl() {
		return getTinaFwConfig("base_url");
	}

	/**
	 * @return the property "tinafw.timeout.implicit_wait"
	 */
	public static int getImplicitWait() {
		return Integer.parseInt(getTinaFwConfig("timeout.implicit_wait"));
	}

	/**
	 * @return the property "tinafw.timeout.explicit_wait"
	 */
	public static int getExplicitWait() {
		return Integer.parseInt(getTinaFwConfig("timeout.explicit_wait"));
	}

	/**
	 * @return the property "tinafw.keep_browsers_open"
	 * @see PropertiesUtils#toBoolean(String)
	 */
	public static boolean getKeepBrowsersOpen() {
		String key = "keep_browsers_open";
		return PropertiesUtils.toBoolean(getTinaFwConfig(key));
	}

	/**
	 * @return the property "tinafw.max_executions"
	 */
	public static int getMaxExecutions() {
		return Integer.parseInt(getTinaFwConfig("max_executions"));
	}

	/**
	 * @return the property "tinafw.reports_dir"
	 */
	public static String getReportsDir() {
		return getTinaFwConfig("reports_dir");
	}
	
	/**
	 * @return a List of the {@link SupportedBrowser}'s specified in the prop
	 *         'tinafw.browsers'
	 * 
	 * @throws IllegalArgumentException if the property 'tinafw.browsers' is not valid,
	 *         i.e. one of its values cannot be converted to {@link SupportedBrowser},
	 *         or there are no browsers specified
	 * 
	 * @see PropertiesUtils#split(String)       
	 */
	public static List<SupportedBrowser> getBrowsers() {
		String[] browserNames = PropertiesUtils.split(getTinaFwConfig("browsers"));
		if(browserNames.length == 0)
			throw new IllegalArgumentException(
					"no browsers specified in the property '"
					+ NAMESPACE + ".browsers'");
		List<SupportedBrowser> browsers = new ArrayList<>();
		for (String browserName : browserNames) {
			SupportedBrowser browser = SupportedBrowser.valueOf(browserName.toUpperCase());
			if(!browsers.contains(browser))
				browsers.add(browser);
		}
		return browsers;
	}
	
	/**
	 * @return the {@link Platform} as specified in the property
	 *         "tinafw.grid.platform"
	 */
	public static Platform getGridPlatform() {
		return Platform.valueOf(
				getTinaFwConfig("grid.platform").toUpperCase());	
	}
	
	/**
	 * @return the property "tinafw.grid.browser_version"
	 */
	public static String getGridBrowserVersion() {
		return getTinaFwConfig("grid.browser_version");	
	}

	/**
	 * @return the property "tinafw.grid.hub_url"
	 * @see PropertiesUtils#toURL(String)
	 */
	public static URL getGridHubUrl() {
		return PropertiesUtils.toURL(getTinaFwConfig("grid.hub_url"));
	}
	
	
	/**
	 * Convenience method for getting a config in the namespace "tinafw".
	 * The prefix "tinafw." is prepended for you.
	 * 
	 * @param propName the name of the wanted config (namespace "tinafw." excluded)
	 * @see PropertyLoader#getProperty(String, String)
	 */
	private static String getTinaFwConfig(String propName) {
		return propLoader.getProperty(NAMESPACE, propName);
	}

}
