package me.alb_i986.selenium.tinafw.tests;

import org.openqa.selenium.*;

/**
 * Static class providing utility methods for tests.
 *
 */
public class TestHelper {
	
	private TestHelper() {}

	/**
	 * @see TakesScreenshot#getScreenshotAs(OutputType)
	 */
	public static <T> T getScreenshotAs(OutputType<T> outputType, WebDriver driver) {
		return ((TakesScreenshot) driver).getScreenshotAs(outputType);
	}
	
}
