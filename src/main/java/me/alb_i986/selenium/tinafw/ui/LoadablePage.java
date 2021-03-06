package me.alb_i986.selenium.tinafw.ui;

import java.lang.reflect.InvocationTargetException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

/**
 * A page that can be accessed directly, from a URL with no run-time parameters,
 * e.g. "/articles"; not "/article/XX"
 * <p>
 * Any implementor must implement (hide) {@link #getRelativeUrl()}.
 *  
 */
public interface LoadablePage extends WebPage {
	
	/**
	 * @return the URL relative to {@link WebPage#BASE_URL}
	 */
	static public String getRelativeUrl() {
		throw new UnsupportedOperationException("this method need to be redefined in the subclass");
	}

	/**
	 * Load the given LoadablePage.
	 * Browse to its URL, create its instance (which includes a wait until the page
	 * is loaded), and finally return it.
	 * 
	 * @param loadablePageClass the class object for the requested page
	 * @param driver 
	 * 
	 * @return the requested LoadablePage
	 * @throws WebDriverException if the page cannot be loaded
	 * @throws IllegalStateException if the page cannot be instantiated
	 */
	static public <T extends LoadablePage> T load(Class<T> loadablePageClass, WebDriver driver) {
		try {
			String relativeUrl = (String) loadablePageClass.getMethod("getRelativeUrl").invoke(null);
			PageHelper.Navigation.browseTo(relativeUrl, driver);
			return 
				(T) loadablePageClass.getConstructor(WebDriver.class, WebPage.class)
					.newInstance(driver, null);
		} catch (InvocationTargetException e) {
			throw new WebDriverException("cannot load loadable page " + loadablePageClass.getSimpleName(), e);
		} catch (InstantiationException | NoSuchMethodException | IllegalAccessException e) {
			throw new IllegalStateException("cannot instantiate loadable page " + loadablePageClass.getSimpleName(), e);
		}
	}

}
