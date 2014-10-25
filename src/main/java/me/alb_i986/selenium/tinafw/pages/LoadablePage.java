package me.alb_i986.selenium.tinafw.pages;

import java.lang.reflect.InvocationTargetException;

import org.openqa.selenium.WebDriver;

/**
 * A page that can be accessed directly, from a URL.
 * <p>
 * Any implementor must implement (hide) {@link #getRelativeUrl()}.
 *  
 * @see #load(Class, WebDriver)
 */
public interface LoadablePage extends Page {
	
	/**
	 * @return the URL relative to {@link Page#BASE_URL}.
	 */
	static public String getRelativeUrl() {
		throw new UnsupportedOperationException("this method is not implemented in the subclass");
	}

	/**
	 * Load the given LoadablePage (i.e. browse to it),
	 * create its instance, and return it.
	 * @param driver 
	 * 
	 * @return the requested LoadablePage
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 */
	static public <T extends LoadablePage> T load(Class<T> c, WebDriver driver)
			throws InstantiationException, IllegalAccessException,
				IllegalArgumentException, InvocationTargetException,
				NoSuchMethodException, SecurityException {
		String url = (String) c.getMethod("getRelativeUrl").invoke(null);
		PageHelper.Navigation.browseTo(url, driver);
		return 
			(T) c.getConstructor(WebDriver.class, Page.class)
				.newInstance(driver, null);
	}

}
