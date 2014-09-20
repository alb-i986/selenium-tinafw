package me.alb_i986.selenium.tinafw.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.*;
import org.openqa.selenium.support.ui.*;


/**
 * Static class providing helper functions that solve
 * common problems concerning Selenium WebDriver.
 * 
 * The static methods are grouped in static nested classes.
 *
 */
public class PageHelper {

	private static final Logger logger = Logger.getLogger(PageHelper.class);


	/**
	 * Static class providing some helper functions that deal with
	 * explicit waits of Selenium WebDriver.
	 */
	public static final long defaultWaitTimeOutInSeconds = 15;
		
	/**
	 * Explicitly wait until the element identified by the given locator
	 * is visible.
	 * Throw {@link AssertionError} if the wait times out.
	 * 
	 * @param locator
	 * @param driver
	 * 
	 * @throws AssertionError if the wait times out, i.e. the element is not visible
	 */
	public static void assertElementIsDisplayed(By locator, WebDriver driver) {
		try {
			waitUntil(ExpectedConditions.visibilityOfElementLocated(locator), driver);
		} catch(TimeoutException e) {
			throw new AssertionError("[assert KO] element " + locator + " is NOT displayed as expected", e);
		}
		logger.info("[assert OK] element " + locator + " is visible");
	}
	
	/**
	 * Explicitly wait until the given element is visible.
	 * Throw {@link AssertionError} if the wait times out.
	 * 
	 * @param element
	 * @param driver
	 * 
	 * @throws AssertionError if the wait times out, i.e. element is not visible
	 */
	public static void assertElementIsDisplayed(WebElement element, WebDriver driver) {
		try {
			waitUntil(ExpectedConditions.visibilityOf(element), driver);
		} catch(TimeoutException e) {
			throw new AssertionError("[assert KO] element " + element + " is NOT displayed as expected");
		}
		logger.info("[assert OK] element " + element + " is displayed as expected");
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
				refreshPage(driver);
			}
		}
		return null;
	}
	
	/**
	 * Navigate to the current URL.
	 * 
	 * @see WebDriver#get(String)
	 * @see WebDriver#getCurrentUrl()
	 */
	public static void refreshPage(WebDriver driver) {
		driver.get(driver.getCurrentUrl());
	}

	public static void hoverMouseOver(WebElement element, WebDriver driver) {
		new Actions(driver)
		.moveToElement(element)
		.perform();
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
	 * Generic explicit wait, taking an {@link ExpectedCondition} as a parameter.
	 * Times out after {@link #defaultWaitTimeOutInSeconds} seconds.
	 *  
	 * @param expectedCondition
	 * @param driver
	 * 
	 * @see #until(ExpectedCondition, WebDriver, long)
	 */
	public static void waitUntil(ExpectedCondition<?> expectedCondition, WebDriver driver) {
		waitUntil(expectedCondition, driver, defaultWaitTimeOutInSeconds);
	}

	/**
	 * Generic explicit wait, taking an {@link ExpectedCondition} as a parameter.
	 * Times out after the given number of seconds.
	 *  
	 * @param expectedCondition
	 * @param driver
	 * @param timeOutInSeconds
	 */
	public static void waitUntil(ExpectedCondition<?> expectedCondition, WebDriver driver, long timeOutInSeconds) {
		logger.debug("BEGIN Explicit wait: waiting until " + expectedCondition);
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		wait.until(expectedCondition);
		logger.debug("END Explicit wait: " + expectedCondition);
	}


}
