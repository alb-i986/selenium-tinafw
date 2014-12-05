package me.alb_i986.selenium.tinafw.ui;

import org.openqa.selenium.WebDriver;

import me.alb_i986.selenium.tinafw.config.TinafwPropLoader;

/**
 * Interface representing a generic page.
 * <p>
 * The constant {@link #BASE_URL} stores the base URL of the SUT,
 * and it is loaded from the config.
 */
public interface WebPage {

	/**
	 * @see TinafwPropLoader#getBaseUrl()
	 */
	public final static String BASE_URL = TinafwPropLoader.getBaseUrl();
	

	/**
	 * @see WebDriver#getTitle()
	 */
	public String getTitle();

	/**
	 * @see WebDriver#getCurrentUrl()
	 */
	public String getCurrentUrl();

}
