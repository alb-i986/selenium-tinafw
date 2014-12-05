package me.alb_i986.selenium.tinafw.ui;

import java.net.URL;

import me.alb_i986.selenium.tinafw.config.Config;

import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * A singleton provider of {@link WebDriverFactory}.
 */
public class WebDriverFactoryProvider {

	/**
	 * A {@link WebDriverFactory} according to the settings:
	 * <ul>
	 * <li>a {@link WebDriverFactoryLocal} if {@link Config#PROP_GRID_HUB_URL} is
	 *     not defined;
	 * <li>else, a {@link WebDriverFactoryRemote}
	 * <li>finally, decorate it with {@link WebDriverFactoryDecoratorImplicitWait}, if
	 *     {@link Config#PROP_TIMEOUT_IMPLICIT_WAIT} is defined
	 * </ul>
	 */
	public static final WebDriverFactory fromConfig = buildFactoryFromConfig();
	
	private static WebDriverFactory buildFactoryFromConfig() {
		WebDriverFactory tmpFactoryFromConfig;
		URL hubURL = Config.getGridHubUrl();
		if(hubURL == null) {
			tmpFactoryFromConfig = new WebDriverFactoryLocal();
		} else {
			DesiredCapabilities extraDesiredCapabilities = new DesiredCapabilities();
			extraDesiredCapabilities.setVersion(Config.getGridBrowserVersion());
			extraDesiredCapabilities.setPlatform(Config.getGridPlatform());
			tmpFactoryFromConfig = new WebDriverFactoryRemote(hubURL, extraDesiredCapabilities);
		}
		Long implicitWait = Config.getImplicitWait();
		if(implicitWait != null)
			tmpFactoryFromConfig = new WebDriverFactoryDecoratorImplicitWait(implicitWait, tmpFactoryFromConfig);
		return tmpFactoryFromConfig;
	}

}
