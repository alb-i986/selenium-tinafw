package me.alb_i986.selenium.tinafw.domain;

import me.alb_i986.selenium.tinafw.pages.*;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
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
	 * By default inject a {@link WebDriverFactoryLocal} decorated by
	 * {@link WebDriverFactoryDecoratorImplicitWait}.
	 */
	public Browser() {
		this.driverFactory = new WebDriverFactoryDecoratorImplicitWait(
				new WebDriverFactoryLocal());
	}
	
	public Browser(WebDriverFactory driverFactory) {
		if(driverFactory == null)
			throw new IllegalArgumentException("The arg cannot be null");
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
	
	/**
	 * @see PageHelper.Navigation#browseTo(String, WebDriver)
	 */
	public void browseTo(String relativeUrl) {
		assertIsOpen();
		PageHelper.Navigation.browseTo(relativeUrl, driver);
	}
	
	/**
	 * @see TakesScreenshot#getScreenshotAs(OutputType)
	 */
	public <T> T getScreenshotAs(OutputType<T> outputType) {
		assertIsOpen();
		return ((TakesScreenshot)driver).getScreenshotAs(outputType);
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
	
	/**
	 * @throws IllegalStateException if this browser is not open
	 */
	private void assertIsOpen() {
		if(!isOpen())
			throw new IllegalStateException("The browser is not open");
	}

}
