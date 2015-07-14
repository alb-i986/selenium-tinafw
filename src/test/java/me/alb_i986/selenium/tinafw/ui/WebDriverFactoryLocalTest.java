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
		assumeThat(supportedBrowser.toClass(), hasZeroArgConstructor());

		// when
		try {
			driver = sut.getWebDriver(supportedBrowser);
		} catch (Throwable t) {
			if (supportedBrowser == SupportedBrowser.HTML_UNIT) {
				throw t;
			}
			// let's consider any exception (but html unit) as an environment issue
			// E.g.: get IEDriver fails because we are not on Windows
			assumeNoException(t);
		}

		// then
		assertNotNull(driver);
		assertThat(driver, is(instanceOf(supportedBrowser.toClass())));
	}

	private static Matcher<Class<?>> hasZeroArgConstructor() {
		return new TypeSafeMatcher<Class<?>>() {
			@Override
			protected boolean matchesSafely(Class<?> aClass) {
				try {
					aClass.getConstructor();
					return true;
				} catch (NoSuchMethodException e) {
					return false;
				}
			}

			@Override
			public void describeTo(Description description) {
				description.appendText("has a 0-arg constructor");
			}
		};
	}
}
