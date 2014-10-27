package me.alb_i986.selenium.tinafw.tests;

import me.alb_i986.selenium.tinafw.domain.Browser;
import static org.junit.Assert.*;

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
	public static <T> T getScreenshotAs(OutputType<T> outputType, Browser browser) {
		WebDriver driver = assertDriverNotNull(browser);
		return ((TakesScreenshot) driver).getScreenshotAs(outputType);
	}
	
	/**
	 * @return the HTML with an img with the screenshot embedded in base64 format
	 * @see <a href="http://en.wikipedia.org/wiki/Data_URI_scheme#HTML">Data_URI_scheme @ Wikipedia</a>
	 */
	public static String getScreenshotAsHTML(Browser browser) {
		String html = "";
		String screenshot = getScreenshotAs(OutputType.BASE64, browser);
		//TODO StringBuilder for better performance?
		html += "<img width=\"400\" height=\"300\" src=\"data:image/png;base64,";
		html += screenshot;
		html += "\" />\n";
		return html;
	}
	
	/**
	 * @param browser 
	 * @return a String with a readonly textarea containing the page source
	 */
	public static String getPageSourceAsHTML(Browser browser) {
		WebDriver driver = assertDriverNotNull(browser);
		String html = "";
		html += "<textarea rows=\"20\" cols=\"90\" readonly=\"readonly\" >";
		html += driver.getPageSource();
		html += "</textarea>\n";
		return html;
	}

	private static WebDriver assertDriverNotNull(Browser browser) {
		assertNotNull(browser);
		WebDriver driver = browser.getWebDriver();
		assertNotNull(driver);
		return driver;
	}
	
}
