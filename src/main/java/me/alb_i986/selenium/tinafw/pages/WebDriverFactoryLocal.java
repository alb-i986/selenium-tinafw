package me.alb_i986.selenium.tinafw.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class WebDriverFactoryLocal implements WebDriverFactory {
	
	protected static final Logger logger = Logger.getLogger(WebDriverFactoryLocal.class);

	/**
	 * @throws RuntimeException if the instantiation of the WebDriver fails
	 */
	@Override
	public WebDriver getWebDriver() {
		try {
			return (WebDriver) BROWSER_TYPE.toClass().newInstance();
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException e) {
			throw new RuntimeException("cannot create a WebDriver instance for " + BROWSER_TYPE, e);
		}
	}

}
