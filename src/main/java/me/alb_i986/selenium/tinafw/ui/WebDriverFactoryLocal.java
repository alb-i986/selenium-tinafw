package me.alb_i986.selenium.tinafw.ui;

import me.alb_i986.selenium.tinafw.domain.SupportedBrowser;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

/**
 * Factory that creates {@link WebDriver}s which drive browsers on the local machine.
 * <p>
 * To keep the implementation as simple and generic as possible, it is not able to
 * handle browser profiles/options (e.g. {@link FirefoxProfile} or {@link ChromeOptions}).
 */
public class WebDriverFactoryLocal implements WebDriverFactory {
	
	/**
	 * @throws WebDriverFactoryException if the instantiation of the WebDriver fails
	 */
	@Override
	public WebDriver getWebDriver(SupportedBrowser browserType) {
		try {
			return browserType.toClass().newInstance();
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException e) {
			throw new WebDriverFactoryException(
					"cannot create a WebDriver instance for " + browserType, e);
		}
	}

}
