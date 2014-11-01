package me.alb_i986.selenium.tinafw.pages;

import java.net.URL;

import me.alb_i986.selenium.tinafw.domain.SupportedBrowser;
import me.alb_i986.selenium.tinafw.utils.TinafwPropLoader;

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
public class WebDriverFactoryRemote implements WebDriverFactory {

	/**
	 * @see TinafwPropLoader#getGridPlatform()
	 */
	public static final Platform PLATFORM = TinafwPropLoader.getGridPlatform();

	/**
	 * @see TinafwPropLoader#getGridBrowserVersion()
	 */
	public static final String BROWSER_VERSION =
			TinafwPropLoader.getGridBrowserVersion();

	/**
	 * @see TinafwPropLoader#getGridHubUrl()
	 */
	public static final URL GRID_HUB_URL = TinafwPropLoader.getGridHubUrl();


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
		capabilities.setPlatform(PLATFORM);
		capabilities.setVersion(BROWSER_VERSION);
		RemoteWebDriver remoteWebDriver = new RemoteWebDriver(GRID_HUB_URL, capabilities);
		// LocalFileDetector allows for uploading files from the local machine
		// running the tests to the selenium node running the browser
		remoteWebDriver.setFileDetector(new LocalFileDetector());
		return remoteWebDriver;
	}

}
