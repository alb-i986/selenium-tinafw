package me.alb_i986.selenium.tinafw.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class TinafwExpectedConditions {

	protected TinafwExpectedConditions() {
	}

	/**
	 * An expectation for checking that at least one of the elements matching the locator
	 * is visible.
	 *
	 * @param locator used to find the elements
	 * @return the first visible WebElement that is found
	 */
	public static ExpectedCondition<WebElement> visibilityOfOneOfElementsLocatedBy(final By locator) {
		return new ExpectedCondition<WebElement>() {
			@Override
			public WebElement apply(WebDriver driver) {
				for (WebElement element : driver.findElements(locator)) {
					if (element.isDisplayed()) {
						return element;
					}
				}
				return null;
			}

			@Override
			public String toString() {
				return "visibility of one of the elements located by " + locator;
			}
		};
	}
}
