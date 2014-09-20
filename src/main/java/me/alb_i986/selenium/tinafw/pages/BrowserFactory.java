package me.alb_i986.selenium.tinafw.pages;

import java.util.concurrent.TimeUnit;

import me.alb_i986.selenium.tinafw.domain.SupportedBrowser;
import me.alb_i986.selenium.tinafw.utils.PropertyLoader;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;

public class WebDriverFactory {

	public static final int IMPLICIT_WAIT_SECONDS = Integer.valueOf(
			PropertyLoader.getTinaFwConfig("implicit_wait_seconds"));
	
	public static final SupportedBrowser BROWSER_TYPE = SupportedBrowser.valueOf(
			PropertyLoader.getTinaFwConfig("browser").toUpperCase());

	/**
	 * Create and return a WebDriver instance of the given type.
	 * Also, set an implicit wait of {@value WebDriverFactory#IMPLICIT_WAIT_SECONDS}s.
	 * 
	 * @param browserType the supported browser to be created
	 * @throws IllegalArgumentException if the browser specified is not supported
	 */
	public static WebDriver getWebDriver(SupportedBrowser browserType) {
		WebDriver driver;
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
		driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT_SECONDS, TimeUnit.SECONDS);
		return driver;
	}

	/**
	 * @return a {@link WebDriver} according to {@link #BROWSER_TYPE}
	 * 
	 * @see #getWebDriver(SupportedBrowser)
	 */
	public static WebDriver getWebDriver() {
		return getWebDriver(BROWSER_TYPE);
	}

/*
	public static WebDriver getRemoteWebDriver(SupportedBrowser browserType) {
		DesiredCapabilities capabilities;
		// set browser capability
		switch (browserType) {
			case CHROME:
				capabilities = DesiredCapabilities.chrome();
				break;
			case FIREFOX:
				capabilities = DesiredCapabilities.firefox();
				break;
			case SAFARI:
				capabilities = DesiredCapabilities.safari();
				break;
			case IE:
				capabilities = DesiredCapabilities.internetExplorer();
				break;
			default:
				throw new IllegalArgumentException("The specified Browser type (" + browserType + ")"
						+ " is not supported at the moment");
		}
		// set platform capability
		capabilities.setPlatform(platform);
		// create the RemoteWebDriver
		RemoteWebDriver remoteWebDriver = new RemoteWebDriver(seleniumGridHubURL, capabilities);
		// set FileDetector that allows for uploading files through the grid
		remoteWebDriver.setFileDetector(new LocalFileDetector());
		return remoteWebDriver;
*/
	
}
