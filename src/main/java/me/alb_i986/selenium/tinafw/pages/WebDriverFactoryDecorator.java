package me.alb_i986.selenium.tinafw.pages;

import me.alb_i986.selenium.tinafw.domain.SupportedBrowser;

import org.openqa.selenium.WebDriver;

public abstract class WebDriverFactoryDecorator extends WebDriverFactory {
	
	private WebDriverFactory decoratingFactory;
	
	public WebDriverFactoryDecorator(WebDriverFactory decoratingFactory) {
		this.decoratingFactory = decoratingFactory;
	}

	/**
	 * Create and return a WebDriver instance of the given type.
	 * 
	 * @param browserType the supported browser to be created
	 * @throws IllegalArgumentException if the browser specified is not supported
	 */
	@Override
	public WebDriver getWebDriver(SupportedBrowser browserType) {
		return decoratingFactory.getWebDriver(browserType);
	}

	@Override
	public WebDriver getWebDriver() {
		return decoratingFactory.getWebDriver();
	}
	
	
}
