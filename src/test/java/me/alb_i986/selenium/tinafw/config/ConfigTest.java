package me.alb_i986.selenium.tinafw.config;

import static org.junit.Assert.*;

import org.junit.Test;

public class ConfigTest {

	@Test
	public void getOptionaldProperty_givenUndefinedProperty() {
		String undefinedProperty = "undefined_property";
		String value = Config.getOptionalProperty(undefinedProperty);
		assertNull(value);
	}

	@Test(expected = SettingNotFoundException.class)
	public void getRequiredProperty_givenUndefinedProperty() {
		// given an undefined property
		String undefinedProperty = "undefined_property";
		// when getRequiredProperty of undefined_property
		Config.getRequiredProperty(undefinedProperty);
		// then an exception should be thrown
	}

}
