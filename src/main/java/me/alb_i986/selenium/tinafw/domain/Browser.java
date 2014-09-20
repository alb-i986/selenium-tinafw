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
	
	private WebDriver driver;

	public Browser() {
	}

	/**
	 * Create an instance of WebDriver.
	 * 
	 * @see WebDriverFactory#getWebDriver()
	 */
	public void open() {
		if(driver != null)
			throw new IllegalStateException("Browser already open: close it first");
		driver = WebDriverFactory.getWebDriver();
	}
	
	/**
	 * @see WebDriver#quit()
	 */
	public void close() {
		if(driver != null) {
			driver.quit();
			driver = null;
		}
	}
	
	/**
	 * Start a session by browsing to the homepage.
	 * @return HomePage
	 */
	public HomePage startSession() {
		return HomePage.get(driver);
	}

	public WebDriver getWebDriver() {
		return driver;
	}

}
