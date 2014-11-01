package me.alb_i986.selenium.tinafw.utils;

import java.net.MalformedURLException;
import java.net.URL;

public class PropertiesUtils {

	protected PropertiesUtils() {}
	
	/**
	 * Split the given string by comma or semicolon.
	 * Also, strip any spaces before or after the separator.
	 * <p>
	 * Examples:
	 * <ul>
	 * <li>"asd ; asdf" -&gt; [ "asd", "asdf" ]</li>
	 * <li>"asd ;,   asdf" -&gt; [ "asd", "", "asdf" ]</li>
	 * <li>warning: " ;, " -&gt; [ ]</li>
	 * </ul>
	 * 
	 * @param multiValueProp a comma/semicolon-separated string
	 * @return an array of strings
	 * 
	 * @see String#split(String)
	 */
	public static String[] split(String multiValueProp) {
		return multiValueProp.split(" *[,;] *");
	}

	/**
	 * @param url
	 * @return a {@link URL}
	 * @throws ConfigException if {@link MalformedURLException} is raised
	 */
	public static URL toURL(String url) {
		try {
			return new URL(url);
		} catch (MalformedURLException e) {
			throw new ConfigException(url + " cannot be converted to a URL", e);
		}
	}

	/**
	 * @throws ConfigException if value is an empty string
	 */
	public static boolean toBoolean(String value) {
		if(value.equals(""))
			throw new ConfigException("empty string");
		return Boolean.parseBoolean(value);
	}

}
