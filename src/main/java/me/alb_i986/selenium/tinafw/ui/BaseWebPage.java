package me.alb_i986.selenium.tinafw.ui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

/**
 * Abstract class representing a generic page.
 * A Page has a {@link WebDriver}. 
 * <p> 
 * As soon as a page object is created, an explicit wait
 * ({@link #waitUntilIsLoaded()}) is triggered.
 * <p>
 * Any subclass should override {@link #waitUntilIsLoaded()}
 * to implement a more meaningful wait for that page.
 *
 */
public abstract class BaseWebPage implements WebPage {

	protected static final Logger logger = LogManager.getLogger(WebPage.class);

	protected WebDriver driver;
	protected WebPage previousPage;
	
	/**
	 * Calls {@link PageFactory#initElements(WebDriver, Object)}
	 * and {@link #waitUntilIsLoaded()}.
	 * 
	 * @param driver
	 * @param previous
	 */
	public BaseWebPage(WebDriver driver, WebPage previous) {
		this.driver = driver;
		this.previousPage = previous;
		PageFactory.initElements(driver, this);
		logger.info("Loading page " + getClass().getSimpleName());
		waitUntilIsLoaded();
	}
	

	/**
	 * @see WebDriver#getTitle()
	 */
	@Override
	public String getTitle() {
		return driver.getTitle();
	}

	/**
	 * @see WebDriver#getCurrentUrl()
	 */
	@Override
	public String getCurrentUrl() {
		return driver.getCurrentUrl();
	}

	/**
	 * @param locator
	 * @return {@link WebElement}
	 * 
	 * @see WebDriver#findElement(By)
	 */
	protected WebElement getElement(By locator) {
		return driver.findElement(locator);
	}

	/**
	 * @param locator
	 * @return list of {@link WebElement}s
	 * 
	 * @see WebDriver#findElements(By)
	 */
	protected List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}

	/**
	 * Dumb wait until the HTML body element is visible.
	 * Actually this method should be abstract,
	 * but it isn't just for convenience.
	 * It is highly recommended to override it.
	 * Or you may just override {@link #getIdentifyingLocator()}.
	 */
	protected void waitUntilIsLoaded() {
		PageHelper.waitUntil(
			ExpectedConditions.visibilityOfElementLocated(getIdentifyingLocator()),
			driver
		);
	}

	protected By getIdentifyingLocator() {
		return By.tagName("body");
	}
	
}
