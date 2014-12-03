package me.alb_i986.selenium.tinafw.ui;

import me.alb_i986.selenium.tinafw.domain.SupportedBrowser;

import org.openqa.selenium.WebDriver;

public abstract class WebDriverFactoryDecorator implements WebDriverFactory {
	
	private WebDriverFactory baseFactory;
	
	public WebDriverFactoryDecorator(WebDriverFactory baseFactory) {
		if(baseFactory == null)
			throw new IllegalArgumentException("The base factory cannot be null.");
		this.baseFactory = baseFactory;
	}

	@Override
	public WebDriver getWebDriver(SupportedBrowser browserType) {
		return baseFactory.getWebDriver(browserType);
	}
	
}
