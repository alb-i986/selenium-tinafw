package me.alb_i986.selenium.tinafw.pages;

import org.openqa.selenium.WebDriver;

public abstract class WebDriverFactoryDecorator extends WebDriverFactory {
	
	private WebDriverFactory decoratingFactory;
	
	public WebDriverFactoryDecorator(WebDriverFactory decoratingFactory) {
		this.decoratingFactory = decoratingFactory;
	}

	@Override
	public WebDriver getWebDriver() {
		return decoratingFactory.getWebDriver();
	}
	
	
}
