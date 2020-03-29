package me.alb_i986.selenium.tinafw.ui;

import java.net.URL;

import me.alb_i986.selenium.tinafw.domain.SupportedBrowser;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * Create instances of {@link WebDriver} (specifically, {@link RemoteWebDriver})
 * which can drive a browser on a remote machine, part of a
 * <a href="https://code.google.com/p/selenium/wiki/Grid2">Selenium Grid</a>.
 * <p>
 * Such a factory needs to know the URL of the hub.
 * Clients may also specify a set of
 * <a href="https://code.google.com/p/selenium/wiki/DesiredCapabilities">desired capabilities</a>.
 * <p>
 * The instances of {@link WebDriver}s will also feature a {@link LocalFileDetector},
 * which handles the upload of files from the local machine running the tests
 * to the selenium node running the browser.
 */
public class WebDriverFactoryRemote implements WebDriverFactory {

	private URL gridHubURL;
	private DesiredCapabilities extraCapabilities;

	/**
	 * A factory creating {@link WebDriver}s matching the given set of extra,
	 * desired capabilities
	 * (see also {@link RemoteWebDriver#RemoteWebDriver(URL, Capabilities)}).
	 * 
	 * @param hubURL
	 * @param extraCapabilities
	 * 
	 * @throws IllegalArgumentException if the desired capabilities specify
	 *         a {@link CapabilityType#BROWSER_NAME}
	 */
	public WebDriverFactoryRemote(URL hubURL, DesiredCapabilities extraCapabilities) {
		this(hubURL);
		if(extraCapabilities.is(CapabilityType.BROWSER_NAME))
			throw new IllegalArgumentException("Desired capabilities cannot specify a browser name");
		this.extraCapabilities = extraCapabilities;
	}

	/**
	 * A factory creating {@link WebDriver}s against the given hub.
	 * 
	 * @param hubURL
	 */
	public WebDriverFactoryRemote(URL hubURL) {
		this.gridHubURL = hubURL;
	}

	@Override
	public WebDriver getWebDriver(SupportedBrowser browserType) {
		DesiredCapabilities capabilities = browserType.toCapabilities();
		capabilities.merge(extraCapabilities);
		RemoteWebDriver remoteDriver = new RemoteWebDriver(gridHubURL, capabilities);
		remoteDriver.setFileDetector(new LocalFileDetector());
		return remoteDriver;
	}

}
