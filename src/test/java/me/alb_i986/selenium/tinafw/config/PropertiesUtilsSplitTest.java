package me.alb_i986.selenium.tinafw.config;

import static org.junit.Assert.*;
import me.alb_i986.selenium.tinafw.config.PropertiesUtils;

import org.junit.Test;

public class PropertiesUtilsSplitTest {

	@Test
	public void javadocExample1() {
		String multiValues = "asd ; asdf";
		String[] splitted = multipleValuesBasicTest(multiValues);
		assertEquals(2, splitted.length);
		assertEquals("asd", splitted[0]);
		assertEquals("asdf", splitted[1]);
	}

	@Test
	public void javadocExample2() {
		String multiValues = "asd ;,   asdf";
		String[] splitted = multipleValuesBasicTest(multiValues);
		assertEquals(3, splitted.length);
		assertEquals("asd", splitted[0]);
		assertEquals("", splitted[1]);
		assertEquals("asdf", splitted[2]);
	}

	@Test
	public void javadocExample3() {
		String onlySeparators = "; ,";
		String[] splitted = multipleValuesBasicTest(onlySeparators);
		assertEquals(0, splitted.length);
	}

	@Test
	public void givenTwoValuesWithNoSpaces() {
		String twoValuesWithNoSpaces = "asd  ,  asdf";
		String[] splitted = multipleValuesBasicTest(twoValuesWithNoSpaces);
		assertEquals(2, splitted.length);
		assertEquals("asd", splitted[0]);
		assertEquals("asdf", splitted[1]);
	}

	@Test
	public void givenSingleValueWithoutSpaces() {
		String oneWord = "asdkjD9sidgh";
		singleValueBasicTest(oneWord);
	}

	@Test
	public void givenSingleValueWithSpaces() {
		String singleValueWithSpaces = "as   dkjs  sidgh ";
		singleValueBasicTest(singleValueWithSpaces);
	}

	@Test
	public void givenMultipleValues() {
		String multiValues = "as d,,;    asdf; asdfg";
		String[] splitted = multipleValuesBasicTest(multiValues);
		assertEquals(5, splitted.length);
		assertEquals("as d", splitted[0]);
		assertEquals("", splitted[1]);
		assertEquals("", splitted[2]);
		assertEquals("asdf", splitted[3]);
		assertEquals("asdfg", splitted[4]);
	}
	
	/**
	 * Apply {@link PropertiesUtils#split(String)} to the parameter.
	 * Then assert that the following conditions hold for the returned array: 
	 * <ul>
	 * <li>has length 1</li>
	 * <li>does not contain ';' nor ','</li>
	 * <li>equals the given value</li>
	 * </ul>
	 * Finally return the splitted array, in order to allow the client to
	 * do further assertions. 
	 * 
	 * @param singleValue
	 * @return the array as returned by {@link PropertiesUtils#split(String)}
	 */
	private String[] singleValueBasicTest(String singleValue) {
		assertFalse("precondition not met", singleValue.contains(","));
		assertFalse("precondition not met", singleValue.contains(";"));
		String[] splitted = PropertiesUtils.split(singleValue);
		assertEquals(1, splitted.length);
		assertFalse(splitted[0].contains(","));
		assertFalse(splitted[0].contains(";"));
		assertEquals(singleValue, splitted[0]);
		return splitted;
	}

	/**
	 * Assert that each element of the array returned by split
	 * does not contain "," nor ";".
	 * Finally return the splitted array, in order to allow the client to
	 * do further assertions.
	 * 
	 * @param multiValues
	 * @return the array as returned by {@link PropertiesUtils#split(String)}
	 */
	private String[] multipleValuesBasicTest(String multiValues) {
		String[] splitted = PropertiesUtils.split(multiValues);
		for (String token : splitted) {
			assertFalse(token.contains(","));
			assertFalse(token.contains(";"));
		}
		return splitted;
	}

}
