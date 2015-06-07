package me.alb_i986.selenium.tinafw.domain;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import me.alb_i986.selenium.tinafw.ui.WebDriverFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.WebDriver;

@RunWith(MockitoJUnitRunner.class)
public class BrowserTest {

	@Mock private WebDriver mockedDriver;
	@Mock private WebDriverFactory driverFactoryStub;

	private Browser browser;

	@Before
	public void before() {
		this.browser = new Browser(driverFactoryStub);
	}
	
	
	/**
	 * Testing post conditions of {@link Browser#open(SupportedBrowser)}:
	 * <ol>
	 * <li>Given a Browser with a null WebDriver</li>
	 * <li>When I open {@link SupportedBrowser#HTML_UNIT}</li>
	 * <li>Then {@link Browser#getWebDriver()} should be not null</li>
	 * <li>And {@link Browser#isOpen()} should return true</li>
	 * </ol>
	 */
	@Test
	public void givenNullDriverWhenOpenBrowserThenDriverIsNotNull() {
		given(driverFactoryStub.getWebDriver(any())).willReturn(mockedDriver);
		browser.setDriver(null);

		// when
		browser.open(SupportedBrowser.HTML_UNIT);

		assertNotNull(browser.getWebDriver());
		assertTrue(browser.isOpen());
	}

	/**
	 * Testing post conditions of {@link Browser#Browser(WebDriverFactory)}:
	 * <ol>
	 * <li>When I instantiate Browser</li>
	 * <li>Then {@link Browser#getWebDriver()} should be null</li>
	 * <li>And {@link Browser#isOpen()} should return false</li>
	 * </ol>
	 */
	@Test
	public void whenNewBrowserThenBrowserShouldBeClosed() {
		// then
		assertBrowserIsClosed();
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

		// when
		browser.close();

		// then
		assertBrowserIsClosed();
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
		browser.setDriver(mockedDriver);

		// when
		browser.close();

		// then
		assertBrowserIsClosed();
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

	private void assertBrowserIsClosed() {
		assertNull(browser.getWebDriver());
		assertFalse(browser.isOpen());
	}

	
}
