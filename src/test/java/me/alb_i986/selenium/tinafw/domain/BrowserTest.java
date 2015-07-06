package me.alb_i986.selenium.tinafw.domain;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import me.alb_i986.selenium.tinafw.ui.WebDriverFactory;
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
	public void setupMocks() {
		given(driverFactoryStub.getWebDriver(any())).willReturn(mockedDriver);
		browser = new Browser(driverFactoryStub);
	}

	@Test
	public void open_givenNewBrowser() {
		// when
		browser.open(SupportedBrowser.HTML_UNIT);

		// then
		assertNotNull(browser.getWebDriver());
		assertTrue(browser.isOpen());
	}

	@Test
	public void close_givenClosedBrowser() {
		// when
		browser.close();

		// then
		assertBrowserIsClosed();
	}

	@Test
	public void close_givenOpenBrowser() {
		// given
		browser.open(SupportedBrowser.HTML_UNIT);

		// when
		browser.close();

		// then
		assertBrowserIsClosed();
	}

	@Test(expected = IllegalStateException.class)
	public void browseTo_givenClosedBrowser() {
		// when
		browser.browseTo("http://www.google.com");

		// then exception is thrown
	}

	@Test
	public void whenNewBrowser() {
		assertBrowserIsClosed();
	}

	private void assertBrowserIsClosed() {
		assertNull(browser.getWebDriver());
		assertFalse(browser.isOpen());
	}
}
