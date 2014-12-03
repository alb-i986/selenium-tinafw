package me.alb_i986.selenium.tinafw.ui;

import me.alb_i986.selenium.tinafw.domain.SupportedBrowser;

import org.openqa.selenium.WebDriver;

/**
 * A factory of {@link WebDriver}'s.
 * <p>
 * Modeled with a
 * <a href="http://en.wikipedia.org/wiki/Decorator_pattern">Decorator</a>.
 */
public interface WebDriverFactory {

	/**
	 * @param browserType
	 * @return a {@link WebDriver} according to the parameter
	 */
	public WebDriver getWebDriver(SupportedBrowser browserType);

}
