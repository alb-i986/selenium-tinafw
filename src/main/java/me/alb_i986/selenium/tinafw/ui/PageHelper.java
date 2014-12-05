package me.alb_i986.selenium.tinafw.ui;

import me.alb_i986.selenium.tinafw.utils.TinafwPropLoader;

import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.*;
import org.openqa.selenium.support.ui.*;


/**
 * Static class providing some helper functions that solve
 * common problems concerning Selenium WebDriver.
 * 
 * The static methods are grouped in static nested classes.
 *
 */
public class PageHelper {

	private static final Logger logger = Logger.getLogger(PageHelper.class);

	/**
	 * The default timeout in seconds used by
	 * {@link #waitUntil(ExpectedCondition, WebDriver)}.
	 *
	 * @see TinafwPropLoader#getExplicitWait()
	 */
	public static final int DEFAULT_EXPLICIT_WAIT_TIMEOUT_SECONDS =
			TinafwPropLoader.getExplicitWait();
			
	/**
	 * Generic explicit wait, taking an {@link ExpectedCondition} as a parameter.
	 * Times out after {@link #DEFAULT_EXPLICIT_WAIT_TIMEOUT_SECONDS} seconds.
	 *  
	 * @param expectedCondition
	 * @param driver
	 * @return whatever WebDriverWait#until returns
	 * 
	 * @see #waitUntil(ExpectedCondition, WebDriver, long)
	 */
	public static <T> T waitUntil(ExpectedCondition<T> expectedCondition, WebDriver driver) {
		return waitUntil(expectedCondition, driver, DEFAULT_EXPLICIT_WAIT_TIMEOUT_SECONDS);
	}

	/**
	 * Generic explicit wait, taking an {@link ExpectedCondition} as a parameter.
	 * Times out after the given number of seconds.
	 *  
	 * @param expectedCondition
	 * @param driver
	 * @param timeOutInSeconds
	 * @return whatever WebDriverWait#until returns
	 * 
	 * @see WebDriverWait#until(com.google.common.base.Function)
	 * @throws TimeoutException if the timeout expires
	 */
	public static <T> T waitUntil(ExpectedCondition<T> expectedCondition,
			WebDriver driver, long timeOutInSeconds) {
		logger.debug("BEGIN wait (timeout=" + timeOutInSeconds + "s) for "
			+ expectedCondition);
		T object = 
			new WebDriverWait(driver, timeOutInSeconds)
				.until(expectedCondition);
		logger.debug("END wait for  " + expectedCondition);
		return object;
	}
	
	/**
	 * Explicitly wait until the given expected condition is met.
	 * Throw {@link AssertionError} if the wait times out.
	 * 
	 * @param condition
	 * @param driver
	 * @return whatever #waitUntil returns
	 * @throws AssertionError if the condition is not met
	 */
	public static <T> T assertExpectedCondition(ExpectedCondition<T> condition, WebDriver driver) {
		try {
			return waitUntil(condition, driver);
		} catch(TimeoutException e) {
			throw new AssertionError("Explicit wait timed out. The condition '" + condition + "' was not met" , e);
		}
	}
	
	/**
	 * Explicitly wait until the given element is visible.
	 * If it is, throw {@link AssertionError}; else, do nothing.
	 * 
	 * @param locator
	 * @param driver
	 * 
	 * @throws AssertionError if element is visible
	 */
	public static void assertElementIsNotDisplayed(By locator, WebDriver driver) {
		try {
			waitUntil(ExpectedConditions.visibilityOfElementLocated(locator), driver);
			throw new AssertionError("[assert KO] element " + locator + " is displayed; expected: NOT displayed");
		} catch(TimeoutException e) {
			logger.info("[assert OK] element " + locator + " is NOT displayed as expected");
		}
	}

	/**
	 * Implicitly wait for an element.
	 * Then, if the element cannot be found, refresh the page
	 * and try finding the element once again.
	 * Finally, return the element.
	 * 
	 * @return the element identified by the given locator;
	 * 	null if the element is not found after the last iteration 
	 * 
	 * @see PageHelper#loopFindOrRefresh(int, By, WebDriver)
	 */
	public static WebElement findOrRefresh(By locator, WebDriver driver) {
		return loopFindOrRefresh(1, locator, driver);
	}
	
	/**
	 * Implicitly wait for an element.
	 * Then, if the element cannot be found, refresh the page.
	 * Try finding the element again, reiterating for maxRefreshes
	 * times or until the element is found.
	 * Finally, return the element.
	 * 
	 * @param maxRefreshes max num of iterations of the loop
	 * @param locator the locator for the element we want to find
	 * @param driver
	 * @return the element identified by the given locator;
	 * 					null if the element is not found after the last iteration 
	 */
	public static WebElement loopFindOrRefresh(int maxRefreshes, By locator, WebDriver driver) {
		for (int i = 0; i < maxRefreshes; i++) {
			WebElement element;
			try {
				// implicitly wait
				element = driver.findElement(locator);
				// if no exception is thrown, then we can exit the loop
				return element;
			} catch(NoSuchElementException e) {
				// if implicit wait times out, then refresh page and continue the loop
				logger.info("after implicit wait, element " + locator + " is still not present: refreshing page and trying again");
				Navigation.refreshPage(driver);
			}
		}
		return null;
	}
	
	public static void hoverMouseOver(WebElement element, WebDriver driver) {
		new Actions(driver)
			.moveToElement(element)
			.perform()
		;
	}
		

	/**
	 * Helper methods about navigating the web app, e.g.
	 * browsing to a URL, refreshing the page, and so on.
	 */
	public static class Navigation {
		
		/**
		 * @see WebDriver.Navigation#refresh()
		 */
		public static void refreshPage(WebDriver driver) {
			driver.navigate().refresh();
		}


		/**
		 * Append the given relative URL to {@link WebPage#BASE_URL}
		 * and navigate there.
		 * 
		 * @param relativeUrl e.g. "/article/999"
		 *          (a '/' will be prefixed if not present) 
		 */
		public static void browseTo(String relativeUrl, WebDriver driver) {
			String url = 
				WebPage.BASE_URL +
				// add a '/' if it's not present neither in Page.BASE_URL. nor in relativeUrl
				(!relativeUrl.startsWith("/") && !WebPage.BASE_URL.endsWith("/") ? "/" : "") +
				relativeUrl.trim()
			;
			driver.get(url);
			logger.debug("URL " + url + " loaded");
		}

		/**
		 * @see WebDriver.Navigation#back()
		 */
		public static void browseBack(WebDriver driver) {
			driver.navigate().back();
		}
	}

	/**
	 * Static class providing some helper functions that solve
	 * common problems concerning forms.
	 */
	public static class Form {

		/**
		 * Enter the text with {@link WebElement#sendKeys(CharSequence...)} and loop
		 * (max 50 attempts) until the text is found to have been actually fully entered.
		 * Needed when for some reason (maybe some JS making WebDriver lose focus from the field)
		 * {@link WebElement#sendKeys(CharSequence...)} does not do its job and does not send all the
		 * characters it was asked to.
		 * 
		 * @param text the text to enter in the text field
		 * @param textField a text field
		 * @throws WebDriverException after 50 unsuccessful loops
		 */
		public static void enterTextLoop(String text, WebElement textField) {
			int i = 0;
			int maxAttempts = 50;
			while(i<maxAttempts && ! textField.getAttribute("value").equals(text)) {
				enterText(text, textField);
				logger.debug("after sendKeys, textField contains: " + textField.getAttribute("value"));
				i++;
			}
			if(i == maxAttempts)
				throw new WebDriverException("can't enter text fully; giving up after " + maxAttempts + " attempts");
		}

		/**
		 * Wrapper for {@link WebElement#clear()} and
		 * {@link WebElement#sendKeys(CharSequence...)}
		 * @param text
		 * @param textField
		 */
		public static void enterText(String text, WebElement textField) {
			textField.clear();
			textField.sendKeys(text);
		}
	}

	/**
	 * Typical use is to simulate hitting ESCAPE or ENTER.
	 *  
	 * @param driver
	 * @param keys
	 * 
	 * @see Actions#sendKeys(CharSequence...)
	 */
	public static void hitKeys(WebDriver driver, CharSequence keys) {
		new Actions(driver)
			.sendKeys(keys)
			.perform()
		;
	}

}
