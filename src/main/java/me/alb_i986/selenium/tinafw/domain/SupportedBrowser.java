package me.alb_i986.selenium.tinafw.domain;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;

public enum SupportedBrowser {

	CHROME(ChromeDriver.class, DesiredCapabilities.chrome()),
	FIREFOX(FirefoxDriver.class, DesiredCapabilities.firefox()),
	SAFARI(SafariDriver.class, DesiredCapabilities.safari()),
	IE(InternetExplorerDriver.class, DesiredCapabilities.internetExplorer()),
	HTML_UNIT(HtmlUnitDriver.class, DesiredCapabilities.htmlUnit()),
	;

	private DesiredCapabilities capabilities;
	private Class<?> clazz;

	private SupportedBrowser(Class<?> clazz, DesiredCapabilities capabilities) {
		this.clazz = clazz;
		this.capabilities = capabilities;
	}
	
	/**
	 * @see Class#getName()
	 */
	public String toClassName() {
		return clazz.getName();
	}

	/**
	 * @return the concrete WebDriver class corresponding to this enum
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public Class<WebDriver> toClass() throws ClassNotFoundException {
		return (Class<WebDriver>) Class.forName(toClassName());
	}

	public DesiredCapabilities toCapabilities() {
		return this.capabilities;
	}

	/**
	 * @return the lower cased name of the enum constant
	 */
	@Override
	public String toString() {
		return super.toString().toLowerCase();
	}
}