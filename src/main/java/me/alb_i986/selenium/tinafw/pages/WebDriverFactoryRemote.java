package me.alb_i986.selenium.tinafw.pages;

import java.net.MalformedURLException;
import java.net.URL;

import me.alb_i986.selenium.tinafw.utils.ConfigException;
import me.alb_i986.selenium.tinafw.utils.PropertyLoader;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * Create instances of {@link RemoteWebDriver} with the given
 * {@link #PLATFORM}, {@link #BROWSER_VERSION}, as per config.
 * <p>
 * Also, set {@link LocalFileDetector} which allows for uploading files
 * from the local machine that runs the tests to the selenium node running
 * the browser.
 */
public class WebDriverFactoryRemote extends WebDriverFactory {

	/**
	 * Configurable via the property "tinafw.grid.platform".
	 */
	public static final Platform PLATFORM = Platform.valueOf(
			PropertyLoader.getTinaFwConfig("grid.platform").toUpperCase());

	/**
	 * Configurable via the property "tinafw.grid.browser_version".
	 */
	public static final String BROWSER_VERSION = 
			PropertyLoader.getTinaFwConfig("grid.browser_version");

	/**
	 * Configurable via the property "tinafw.grid.hub_url".
	 */
	public static final URL GRID_HUB_URL;

	static {
		try {
			GRID_HUB_URL = new URL(PropertyLoader.getTinaFwConfig("grid.hub_url"));
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
		capabilities.setPlatform(PLATFORM);
		capabilities.setVersion(BROWSER_VERSION);
		RemoteWebDriver remoteWebDriver = new RemoteWebDriver(GRID_HUB_URL, capabilities);
		// LocalFileDetector allows for uploading files from the local machine
		// running the tests to the selenium node running the browser
		remoteWebDriver.setFileDetector(new LocalFileDetector());
		return remoteWebDriver;
	}

}
