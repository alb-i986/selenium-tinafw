package me.alb_i986.selenium.tinafw.domain;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class BrowserTest {

	private Browser browser;

	@Before
	public void before() {
		this.browser = new Browser();
	}
	
	
	/**
	 * Testing post conditions of {@link Browser#open()}:
	 * <ol>
	 * <li>Given a Browser with a null WebDriver</li>
	 * <li>When I open Chrome</li>
	 * <li>Then {@link Browser#getWebDriver()} should be not null</li>
	 * <li>And {@link Browser#isOpen()} should return true</li>
	 * </ol>
	 */
	@Test
	public void givenNullDriverWhenOpenChromeThenDriverIsNotNull() {
		browser.setDriver(null);
		browser.open(SupportedBrowser.CHROME);
		assertNotNull(browser.getWebDriver());
		assertTrue(browser.isOpen());
	}

	/**
	 * Testing post conditions of {@link Browser#Browser()}:
	 * <ol>
	 * <li>When I instantiate Browser</li>
	 * <li>Then {@link Browser#getWebDriver()} should be null</li>
	 * <li>And {@link Browser#isOpen()} should return false</li>
	 * </ol>
	 */
	@Test
	public void whenNewBrowserThenDriverShouldBeNull() {
		browser = new Browser();
		assertNull(browser.getWebDriver());
		assertFalse(browser.isOpen());
	}

	
	/**
	 * Testing post conditions of {@link Browser#close()}:
	 * <ol>
	 * <li>Given a fresh new instance of Browser</li>
	 * <li>When I close the browser</li>
	 * <li>Then {@link Browser#isOpen()} should return false</li>
	 * <li>And {@link Browser#getWebDriver()} should return null</li>
	 * </ol>
	 */
	@Test
	public void givenNullDriverWhenCloseThenBrowserShouldBeClosed() {
		browser.setDriver(null);
		browser.close();
		assertFalse(browser.isOpen());
		assertNull(browser.getWebDriver());
	}

	/**
	 * Testing post conditions of {@link Browser#close()}:
	 * <ol>
	 * <li>Given a Browser with an initial not null WebDriver</li>
	 * <li>When I close the browser</li>
	 * <li>Then {@link Browser#isOpen()} should return false</li>
	 * <li>And {@link Browser#getWebDriver()} should return null</li>
	 * </ol>
	 */
	@Test
	public void givenNotNullDriverWhenCloseThenBrowserShouldBeClosed() {
		browser.setDriver(new HtmlUnitDriver());
		browser.close();
		assertFalse(browser.isOpen());
		assertNull(browser.getWebDriver());
	}


	/**
	 * Trusts (?!) {@link Browser#getWebDriver()} to return the
	 * correct underlying WebDriver, in order to tear down
	 * the test by quitting the driver.
	 */
	@After
	public void after() {
		WebDriver driver = this.browser.getWebDriver();
		if(driver!=null)
			driver.quit();
	}
	
}
