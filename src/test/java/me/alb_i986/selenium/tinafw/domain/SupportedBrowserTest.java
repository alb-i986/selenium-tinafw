package me.alb_i986.selenium.tinafw.domain;

import static org.junit.Assert.*;

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
		String notFoundClasses = "";
		for (SupportedBrowser browserType : SupportedBrowser.values()) {
			try {
				browserType.toClass();
			} catch (ClassNotFoundException e) {
				notFoundClasses += " " + browserType;
			}
		}
		assertEquals("some enums could not be converted: " + notFoundClasses, "", notFoundClasses);
	}

	@After
	public void after() {
	}
	
}
