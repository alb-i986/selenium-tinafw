package me.alb_i986.selenium.tinafw.pages;

import me.alb_i986.selenium.tinafw.domain.SupportedBrowser;
import me.alb_i986.selenium.tinafw.utils.PropertyLoader;

import org.openqa.selenium.WebDriver;

/**
 * Using Decorator to model the WebDriverFactory.
 */
public interface WebDriverFactory {

	/**
	 * Configurable via the property "tinafw.browser".
	 */
	public static final SupportedBrowser BROWSER_TYPE = SupportedBrowser.valueOf(
			PropertyLoader.getTinaFwConfig("browser").toUpperCase());

	/**
	 * @param browserType
	 * @return a {@link WebDriver} according to the parameter
	 * @throws IllegalArgumentException if the browser specified is not supported
	 */
	public WebDriver getWebDriver(SupportedBrowser browserType);

	/**
	 * @return a {@link WebDriver} according to {@link #BROWSER_TYPE}
	 * @see #getWebDriver(SupportedBrowser)
	 */
	default public WebDriver getWebDriver() {
		return getWebDriver(BROWSER_TYPE);
	}

}
