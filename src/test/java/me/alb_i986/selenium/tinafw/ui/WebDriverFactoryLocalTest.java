package me.alb_i986.selenium.tinafw.ui;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.junit.Assume.*;
import static org.junit.runners.Parameterized.*;

import me.alb_i986.selenium.tinafw.domain.SupportedBrowser;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;

import java.util.Arrays;

@RunWith(Parameterized.class)
public class WebDriverFactoryLocalTest {

	@Parameters(name = "{0}")
	public static Iterable<SupportedBrowser> params() {
		return Arrays.asList(SupportedBrowser.values());
	}

	@Parameter
	public SupportedBrowser supportedBrowser;

	private WebDriverFactoryLocal sut = new WebDriverFactoryLocal();
	private WebDriver driver;

	@After
	public void after() {
		if (driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getWebDriver() {
		try {
			// when
			driver = sut.getWebDriver(supportedBrowser);

			// then
			assertNotNull(driver);
			assertThat(driver, is(instanceOf(supportedBrowser.toClass())));

		} catch (Exception e) {
			// let's consider any exception (but html unit) as an environment issue
			// E.g.: get IEDriver fails because we are not on Windows
			if (supportedBrowser == SupportedBrowser.HTML_UNIT) {
				throw e;
			} else {
				assumeNoException(e);
			}
		}
	}
}
