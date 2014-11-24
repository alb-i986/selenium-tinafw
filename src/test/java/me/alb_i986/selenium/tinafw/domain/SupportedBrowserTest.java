package me.alb_i986.selenium.tinafw.domain;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SupportedBrowserTest {


	@Before
	public void before() {
	}
	
	
	/**
	 * Testing {@link SupportedBrowser#toClass()}:
	 * for each enum value, assert that toClass
	 * does not throw ClassNotFoundException.
	 */
	@Test
	public void eachEnumValueShouldBeConvertableToClass() {
		List<SupportedBrowser> failingEnums = new ArrayList<>();
		for (SupportedBrowser browserType : SupportedBrowser.values()) {
			try {
				browserType.toClass();
			} catch (ClassNotFoundException e) {
				failingEnums.add(browserType);
			}
		}
		assertThat("one or more SupportedBrowser's could not be converted",
				failingEnums, Matchers.empty());
	}

	@After
	public void after() {
	}
	
}
