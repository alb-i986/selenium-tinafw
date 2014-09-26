package me.alb_i986.selenium.tinafw.domain;

import me.alb_i986.selenium.tinafw.pages.*;

import org.openqa.selenium.WebDriver;

/**
 * A Browser has a WebDriver.
 * 
 * A Browser may be opened and closed.
 * By opening a Browser, a WebDriver is instantiated.
 * 
 */
public class Browser {

	private WebDriverFactory driverFactory;
	private WebDriver driver;

	/**
	 * By default inject a LocalWebDriverFactory decorated with
	 * WebDriverFactoryDecoratorImplicitWait.
	 */
	public Browser() {
		this.driverFactory = new WebDriverFactoryDecoratorImplicitWait(
				new WebDriverFactoryRemote());
	}
	
	public Browser(WebDriverFactory driverFactory) {
		this.driverFactory = driverFactory;
	}

	/**
	 * Create an instance of WebDriver.
	 * 
	 * @throws IllegalStateException if this browser
	 *         has already been opened
	 * 
	 * @see WebDriverFactory#getWebDriver()
	 */
	public void open() {
		if(isOpen()) {
			throw new IllegalStateException("Browser already open: close it first");
		}
		driver = driverFactory.getWebDriver();
	}
	
	/**
	 * @see WebDriver#quit()
	 */
	public void close() {
		if(isOpen()) {
			driver.quit();
			driver = null;
		}
	}
	
	public void browseTo(String url) {
		driver.get(url);
	}

	/**
	 * @return true if driver is not null.
	 */
	public boolean isOpen() {
		return driver != null;
	}
	
	public WebDriver getWebDriver() {
		return driver;
	}

}
