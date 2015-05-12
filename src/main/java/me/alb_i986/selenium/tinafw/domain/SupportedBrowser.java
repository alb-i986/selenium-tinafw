package me.alb_i986.selenium.tinafw.domain;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;

/**
 * A browser supported by <a href="http://seleniumhq.org">Selenium</a>.
 * <p>
 * This enum provides a couple of useful conversion methods:
 * <ul>
 * <li>{@link #toClass()}, to get the corresponding concrete {@link WebDriver} class,
 * <li>{@link #toCapabilities()}, to get the corresponding {@link DesiredCapabilities}.
 * </ul>
 */
public enum SupportedBrowser {

	CHROME(ChromeDriver.class, DesiredCapabilities.chrome()),
	FIREFOX(FirefoxDriver.class, DesiredCapabilities.firefox()),
	SAFARI(SafariDriver.class, DesiredCapabilities.safari()),
	IE(InternetExplorerDriver.class, DesiredCapabilities.internetExplorer()),
	HTML_UNIT(HtmlUnitDriver.class, DesiredCapabilities.htmlUnit()),
	;

	private DesiredCapabilities capabilities;
	private Class<? extends WebDriver> concreteWebDriverClass;

	private SupportedBrowser(Class<? extends WebDriver> clazz, DesiredCapabilities capabilities) {
		this.concreteWebDriverClass = clazz;
		this.capabilities = capabilities;
	}
	
	/**
	 * @return the concrete WebDriver class corresponding to this SupportedBrowser
	 */
	public Class<? extends WebDriver> toClass() {
		return this.concreteWebDriverClass;
	}

	public DesiredCapabilities toCapabilities() {
		return this.capabilities;
	}

	/**
	 * @return the lower cased name of this enum constant
	 */
	@Override
	public String toString() {
		return super.toString().toLowerCase();
	}
}