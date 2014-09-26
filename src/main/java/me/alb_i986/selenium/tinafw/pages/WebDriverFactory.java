package me.alb_i986.selenium.tinafw.pages;

import me.alb_i986.selenium.tinafw.domain.SupportedBrowser;
import me.alb_i986.selenium.tinafw.utils.PropertyLoader;

import org.openqa.selenium.WebDriver;

public abstract class WebDriverFactory {
	
	public static final SupportedBrowser BROWSER_TYPE = SupportedBrowser.valueOf(
			PropertyLoader.getTinaFwConfig("browser").toUpperCase());

	/**
	 * Create and return a WebDriver instance of the given type.
	 * 
	 * @param browserType the supported browser to be created
	 * @throws IllegalArgumentException if the browser specified is not supported
	 */
	public abstract WebDriver getWebDriver(SupportedBrowser browserType);
	
	/**
	 * @return a {@link WebDriver} according to {@link #BROWSER_TYPE}
	 * 
	 * @see #getWebDriver(SupportedBrowser)
	 */
	public WebDriver getWebDriver() {
		return getWebDriver(BROWSER_TYPE);
	}
}
