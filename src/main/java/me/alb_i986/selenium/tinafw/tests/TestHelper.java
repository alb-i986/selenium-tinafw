package me.alb_i986.selenium.tinafw.tests;

import org.openqa.selenium.*;

/**
 * Static class providing utility methods for tests.
 *
 */
public class TestHelper {
	
	private TestHelper() {}

	/**
	 * @return the screenshot; or null if a WebDriverException would be thrown
	 * @see TakesScreenshot#getScreenshotAs(OutputType)
	 */
	public static <T> T getScreenshotAs(OutputType<T> outputType, WebDriver driver) {
		try {
			return ((TakesScreenshot) driver).getScreenshotAs(outputType);
		} catch(WebDriverException e) {
			return null;
		}
	}

	/**
	 * @return the screenshot as base64;
	 *         or null if something goes wrong, e.g. WebDriverException
	 * 
	 * @see #getScreenshotAs(OutputType, WebDriver)
	 */
	public static String getScreenshotAsBase64(WebDriver driver) {
		if(driver == null)
			return null;
		return getScreenshotAs(OutputType.BASE64, driver);
	}
	
}
