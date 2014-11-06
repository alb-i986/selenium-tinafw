package me.alb_i986.selenium.tinafw.ui;

import java.util.concurrent.TimeUnit;

import me.alb_i986.selenium.tinafw.domain.SupportedBrowser;
import me.alb_i986.selenium.tinafw.utils.TinafwPropLoader;

import org.openqa.selenium.WebDriver;

/**
 * Decorate a WebDriver by setting an implicit wait of
 * {@link #IMPLICIT_WAIT_TIMEOUT_SECONDS} seconds.
 */
public class WebDriverFactoryDecoratorImplicitWait extends WebDriverFactoryDecorator {

	/**
	 * @see TinafwPropLoader#getImplicitWait()
	 */
	public static final int IMPLICIT_WAIT_TIMEOUT_SECONDS = 
			TinafwPropLoader.getImplicitWait();

	public WebDriverFactoryDecoratorImplicitWait(WebDriverFactory decoratingFactory) {
		super(decoratingFactory);
	}

	@Override
	public WebDriver getWebDriver(SupportedBrowser browserType) {
		return setImplicitWait(super.getWebDriver(browserType));
	}
	
	private WebDriver setImplicitWait(WebDriver driver) {
		driver.manage().timeouts().implicitlyWait(
				IMPLICIT_WAIT_TIMEOUT_SECONDS, TimeUnit.SECONDS);
		return driver;
	}
}
