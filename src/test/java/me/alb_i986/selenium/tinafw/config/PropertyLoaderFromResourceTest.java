package me.alb_i986.selenium.tinafw.config;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import me.alb_i986.selenium.tinafw.config.ConfigException;
import me.alb_i986.selenium.tinafw.config.PropertyLoader;
import me.alb_i986.selenium.tinafw.config.PropertyLoaderFromResource;

import org.junit.Test;

public class PropertyLoaderFromResourceTest {

	/**
	 * Testing the constructor
	 * {@link PropertyLoaderFromResource#PropertyLoaderFromResource(String, boolean)}.
	 * 
	 * <ul>
	 * <li>Given a resource which does not exist
	 * <li>When I instantiate a {@link PropertyLoaderFromResource} with
	 *     that resource, and the resource is <i>not</i> required
	 * <li>Then an exception should <i>not</i> be thrown
	 * </ul>
	 */
	@Test
	public void givenNonExistentResourceWhenNewAndResourceIsNotRequired() {
		String resourceName = givenNonExistingResource();
		boolean isResourceRequired = false;
		new PropertyLoaderFromResource(resourceName, isResourceRequired);
		// here it should NOT throw an exception
	}

	/**
	 * Testing the constructor
	 * {@link PropertyLoaderFromResource#PropertyLoaderFromResource(String, boolean)}.
	 * 
	 * <ul>
	 * <li>Given a resource which does not exist
	 * <li>When I instantiate a {@link PropertyLoaderFromResource} with
	 *     that resource, and the resource is required
	 * <li>Then an exception should be thrown
	 * </ul>
	 */
	@Test(expected = ConfigException.class)
	public void givenNonExistentResourceWhenNewAndResourceIsRequired() {
		String resourceName = givenNonExistingResource();
		boolean isResourceRequired = true;
		new PropertyLoaderFromResource(resourceName, isResourceRequired);
		// here it should throw an exception
	}

	/**
	 * Testing {@link PropertyLoaderFromResource#getProperty(String)}.
	 * 
	 * <ul>
	 * <li>Given an existing resource
	 * <li>When I get a defined property
	 * <li>Then the value returned should be not null
	 * </ul>
	 */
	@Test
	public void givenExistingResourceWhenGetExistingProperty() {
		String value = whenGetProperty("existing.property");
		assertNotNull(value);
	}

	/**
	 * Testing {@link PropertyLoaderFromResource#getProperty(String)}.
	 * 
	 * <ul>
	 * <li>Given an existing resource
	 * <li>When I get a property whose value is an empty string
	 * <li>Then the value returned should be an empty string
	 * </ul>
	 */
	@Test
	public void givenExistingResourceWhenGetExistingEmptyProperty() {
		String value = whenGetProperty("empty.property");
		assertNotNull(value);
		assertThat(value, isEmptyString());
	}

	/**
	 * Testing {@link PropertyLoaderFromResource#getProperty(String)}.
	 * 
	 * <ul>
	 * <li>Given an existing resource
	 * <li>When I get a property whose value is a string made up of spaces only (many)
	 * <li>Then the value returned should be an empty string
	 *     (the original spaces should be discarded by the system)
	 * </ul>
	 */
	@Test
	public void givenExistingResourceWhenGetExistingEmptyPropertyWithManySpaces() {
		String value = whenGetProperty("empty.property.with.many.spaces");
		assertNotNull(value);
		assertThat(value, isEmptyString());
	}

	/**
	 * Testing {@link PropertyLoaderFromResource#getProperty(String)}.
	 * 
	 * <ul>
	 * <li>Given an existing resource
	 * <li>When I get a property whose value ends with spaces 
	 * <li>Then the value returned should contain all of the original ending spaces
	 * </ul>
	 */
	@Test
	public void givenExistingResourceWhenGetExistingPropertyWhoseValueHasEndingSpaces() {
		String value = whenGetProperty("empty.property.whose.value.has.ending.spaces");
		assertNotNull(value);
		assertThat(value, is("asd             "));
	}

	private String givenExistingResource() {
		String resourceName = getClass().getSimpleName() + ".properties";
		assertNotNull("The resource " + resourceName + " should exist",
				getClass().getResourceAsStream(resourceName));
		return resourceName;
	}

	private String givenNonExistingResource() {
		String resourceName = "/non-existent-resource.properties";
		assertNull("The resource " + resourceName + " should not exist",
				getClass().getResourceAsStream(resourceName));
		return resourceName;
	}

	private String whenGetProperty(String propertyName) {
		String resourceName = givenExistingResource();
		PropertyLoader propLoader = new PropertyLoaderFromResource(resourceName, true);
		String value = propLoader.getProperty(propertyName);
		return value;
	}

}
