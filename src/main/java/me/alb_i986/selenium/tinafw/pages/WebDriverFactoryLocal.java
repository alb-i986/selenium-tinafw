package me.alb_i986.selenium.tinafw.pages;

import me.alb_i986.selenium.tinafw.domain.SupportedBrowser;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;

public class WebDriverFactoryLocal extends WebDriverFactory {

	/**
	 * Create and return a WebDriver instance of the given type.
	 * Also, set an implicit wait of {@value WebDriverFactoryLocal#IMPLICIT_WAIT_SECONDS}s.
	 * 
	 * @param browserType the supported browser to be created
	 * @throws IllegalArgumentException if the browser specified is not supported
	 */
	@Override
	public WebDriver getWebDriver(SupportedBrowser browserType) {
		WebDriver driver;
		//TODO Class.forName(browserType.toString().toLowerCase())
		switch (browserType) {
			case CHROME:
				driver = new ChromeDriver();
				break;
			case FIREFOX:
				driver = new FirefoxDriver();
				break;
			case SAFARI:
				driver = new SafariDriver();
				break;
			case IE:
				driver = new InternetExplorerDriver();
				break;
			default:
				throw new IllegalArgumentException("The specified Browser type (" + browserType + ")"
							+ " is not supported at the moment");
		}
		return driver;
	}

}
