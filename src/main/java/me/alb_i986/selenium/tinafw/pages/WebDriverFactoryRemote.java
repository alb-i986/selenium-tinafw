package me.alb_i986.selenium.tinafw.pages;

import java.net.MalformedURLException;
import java.net.URL;

import me.alb_i986.selenium.tinafw.domain.SupportedBrowser;
import me.alb_i986.selenium.tinafw.utils.ConfigException;
import me.alb_i986.selenium.tinafw.utils.PropertyLoader;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

public class WebDriverFactoryRemote extends WebDriverFactory {

	private static final Platform platform = Platform.valueOf(
			PropertyLoader.getTinaFwConfig("grid.platform").toUpperCase());
	private static final URL seleniumGridHubURL;

	static {
		try {
			seleniumGridHubURL = new URL(PropertyLoader.getTinaFwConfig("grid.hub_url"));
		} catch (MalformedURLException e) {
			throw new ConfigException("parsing property " +
					PropertyLoader.PROP_NAME_PREFIX + "grid.hub_url" + " failed", e);
		}
	}
	/**
	 * Create and return a WebDriver instance of the given type.
	 * 
	 * @param browserType the supported browser to be created
	 * @throws IllegalArgumentException if the browser specified is not supported
	 */
	@Override
	public WebDriver getWebDriver(SupportedBrowser browserType) {
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
	}

}
