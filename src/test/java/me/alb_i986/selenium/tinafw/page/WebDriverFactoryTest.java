package me.alb_i986.selenium.tinafw.page;

import static org.junit.Assert.*;
import me.alb_i986.selenium.tinafw.domain.SupportedBrowser;
import me.alb_i986.selenium.tinafw.pages.WebDriverFactory;
import me.alb_i986.selenium.tinafw.pages.WebDriverFactoryLocal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class WebDriverFactoryTest {

	private WebDriverFactory factory;

	@Before
	public void before() {
	}
	
	
	/**
	 * Testing post conditions of {@link WebDriverFactoryLocal#getWebDriver(SupportedBrowser)}:
	 * <ol>
	 * <li>Given a fresh new {@link WebDriverFactoryLocal}</li>
	 * <li>When I ask for a WebDriver for {@link SupportedBrowser#HTML_UNIT}</li>
	 * <li>Then {@link WebDriverFactoryLocal#getWebDriver(SupportedBrowser)}
	 *     should return not null and should be an instance of HtmlUnitDriver</li>
	 * </ol>
	 */
	@Test
	public void localFactoryShouldWork() {
		factory = new WebDriverFactoryLocal();
		WebDriver driver = null;
		driver = factory.getWebDriver(SupportedBrowser.HTML_UNIT);
		assertNotNull(driver);
		assertTrue(driver instanceof HtmlUnitDriver);
		driver.quit();
	}

	@After
	public void after() {
	}
	
}
