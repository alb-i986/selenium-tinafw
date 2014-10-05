package me.alb_i986.selenium.tinafw.pages;

import me.alb_i986.selenium.tinafw.utils.PropertyLoader;

/**
 * Interface representing a generic page.
 * <p>
 * The constant {@link #BASE_URL} stores the base URL of the SUT,
 * and it is loaded from the config file.
 */
public interface Page {

	/**
	 * Configurable via the property "tinafw.base_url".
	 */
	public final static String BASE_URL = PropertyLoader.getTinaFwConfig("base_url");
	

	/**
	 * @see WebDriver.Navigation#back()
	 */
	public Page gotoBack();
	
	/**
	 * @see WebDriver#getTitle()
	 */
	public String getTitle();

	/**
	 * @see WebDriver#getPageSource()
	 */
	public String getPageSource();

	/**
	 * @see WebDriver#getCurrentUrl()
	 */
	public String getCurrentUrl();

}
