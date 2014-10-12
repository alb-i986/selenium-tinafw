package me.alb_i986.selenium.tinafw.domain;

import java.lang.reflect.InvocationTargetException;

import me.alb_i986.selenium.tinafw.pages.*;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

/**
 * A Browser has a WebDriver.
 * 
 * A Browser may be opened and closed.
 * By opening a Browser, a WebDriver is instantiated.
 * 
 */
public class Browser {

	protected static final Logger logger = Logger.getLogger(Browser.class);

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
	 * Create an instance of WebDriver, thus open a real browser.
	 * Does nothing if this browser had already been opened.
	 * 
	 * @see WebDriverFactory#getWebDriver()
	 */
	public void open() {
		if(!isOpen())
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
	 * @see PageHelper.Navigation#browseBack(WebDriver)
	 */
	public void browseBack() {
		assertIsOpen();
		PageHelper.Navigation.browseBack(driver);
	}

	/**
	 * Start a session by browsing to the given LoadablePage.
	 * 
	 * @return a LoadablePage
	 * @throws IllegalStateException if the page cannot be loaded/instantiated 
	 * 
	 * @see LoadablePage#getLoadablePage(Class, Browser)
	 */
	public <T extends LoadablePage> T startSession(Class<T> c) {
		try {
			return LoadablePage.getLoadablePage(c, this);
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new IllegalStateException("cannot load loadable page " + c.getSimpleName(), e);
		}
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

	/**
	 * Supposed to be used only by unit tests.
	 */
	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

}
