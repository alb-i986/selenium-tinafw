package me.alb_i986.selenium.tinafw.tests;

import org.openqa.selenium.*;

/**
 * Static class providing utility methods for tests.
 *
 */
public class TestHelper {
	
	private TestHelper() {}

	/**
	 * @return the screenshot;
	 *         if WebDriverException is thrown, return the error message;
	 *         or null, if the arg is null
	 * @see TakesScreenshot#getScreenshotAs(OutputType)
	 */
	public static String getScreenshotAsBase64(WebDriver driver) {
		if(driver == null) {
			return null;
		}
		try {
			return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
		} catch(WebDriverException e) {
			return e.getMessage();
		}
	}

	/**
	 * @param driver
	 * @return a String with the page source;
	 *         if WebDriverException is thrown, return the error message;
	 *         or null, if the arg is null
	 */
	public static String getPageSource(WebDriver driver) {
		if(driver == null)
			return null;
		try {
			return driver.getPageSource();
		} catch(WebDriverException e) {
			return e.getMessage();
		}
	}
}
