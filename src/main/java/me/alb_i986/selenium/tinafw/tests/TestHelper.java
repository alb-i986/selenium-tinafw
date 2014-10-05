package me.alb_i986.selenium.tinafw.tests;

import me.alb_i986.selenium.tinafw.domain.Browser;

import static org.junit.Assert.*;
import org.openqa.selenium.*;

public class TestHelper {

	/**
	 * @see TakesScreenshot#getScreenshotAs(OutputType)
	 */
	public static <T> T getScreenshotAs(OutputType<T> outputType, Browser browser) {
		WebDriver driver = browser.getWebDriver();
		assertNotNull(driver);
		return ((TakesScreenshot) driver).getScreenshotAs(outputType);
	}
	
}
