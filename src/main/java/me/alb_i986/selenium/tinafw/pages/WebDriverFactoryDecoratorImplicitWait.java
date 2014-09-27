package me.alb_i986.selenium.tinafw.pages;

import java.util.concurrent.TimeUnit;

import me.alb_i986.selenium.tinafw.utils.PropertyLoader;

import org.openqa.selenium.WebDriver;

/**
 * Decorate a WebDriver by setting an implicit wait of
 * {@link #IMPLICIT_WAIT_SECONDS} seconds.
 */
public class WebDriverFactoryDecoratorImplicitWait extends WebDriverFactoryDecorator {

	/**
	 * Configurable via the property "tinafw.implicit_wait_seconds".
	 */
	public static final int IMPLICIT_WAIT_SECONDS = Integer.valueOf(
			PropertyLoader.getTinaFwConfig("implicit_wait_seconds"));

	public WebDriverFactoryDecoratorImplicitWait(WebDriverFactory decoratingFactory) {
		super(decoratingFactory);
	}

	@Override
	public WebDriver getWebDriver() {
		return setImplicitWait(super.getWebDriver());
	}
	
	private WebDriver setImplicitWait(WebDriver driver) {
		driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT_SECONDS, TimeUnit.SECONDS);
		return driver;
	}
}
