package me.alb_i986.selenium.tinafw.ui;

import java.util.concurrent.TimeUnit;

import me.alb_i986.selenium.tinafw.domain.SupportedBrowser;

import org.openqa.selenium.WebDriver;

/**
 * Decorate the instantiated WebDriver with an 
 * <a href="http://docs.seleniumhq.org/docs/04_webdriver_advanced.jsp#implicit-waits">
 * implicit wait</a> of the given number of seconds.
 */
public class WebDriverFactoryDecoratorImplicitWait extends WebDriverFactoryDecorator {

	private long seconds;

	public WebDriverFactoryDecoratorImplicitWait(long seconds, WebDriverFactory baseFactory) {
		super(baseFactory);
		this.seconds = seconds;
	}

	@Override
	public WebDriver getWebDriver(SupportedBrowser browserType) {
		return setImplicitWait(super.getWebDriver(browserType));
	}
	
	private WebDriver setImplicitWait(WebDriver driver) {
		driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
		return driver;
	}
}
