package me.alb_i986.selenium.tinafw.pages;

import me.alb_i986.selenium.tinafw.domain.SupportedBrowser;

import org.openqa.selenium.WebDriver;

public abstract class WebDriverFactoryDecorator implements WebDriverFactory {
	
	private WebDriverFactory decoratingFactory;
	
	public WebDriverFactoryDecorator(WebDriverFactory decoratingFactory) {
		this.decoratingFactory = decoratingFactory;
	}

	@Override
	public WebDriver getWebDriver(SupportedBrowser browserType) {
		return decoratingFactory.getWebDriver(browserType);
	}
	
}
