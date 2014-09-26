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

	@Override
	public WebDriver getWebDriver() {
		DesiredCapabilities capabilities;
		// set browser capability
		switch (BROWSER_TYPE) {
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
				throw new IllegalArgumentException("The specified Browser type (" + BROWSER_TYPE + ")"
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
