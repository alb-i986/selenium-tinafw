package me.alb_i986.selenium.tinafw.pages;

import me.alb_i986.selenium.tinafw.domain.SupportedBrowser;

import org.openqa.selenium.WebDriver;

/**
 * Using Decorator to model the WebDriverFactory.
 */
public interface WebDriverFactory {

	/**
	 * @param browserType
	 * @return a {@link WebDriver} according to the parameter
	 * @throws IllegalArgumentException if the browser specified is not supported
	 */
	public WebDriver getWebDriver(SupportedBrowser browserType);

}
