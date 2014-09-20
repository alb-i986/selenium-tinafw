package me.alb_i986.selenium.tinafw.pages;

import org.openqa.selenium.*;


public class HomePage extends Page {

	public HomePage(WebDriver driver, Page previous) {
		super(driver, previous);
	}
	
	public static HomePage get(WebDriver driver) {
		driver.get(BASE_URL);
		return new HomePage(driver, null);
	}
	
	@Override
	protected void waitUntilIsLoaded() {
		//PageHelper.waitUntil(ExpectedConditions.titleIs(EXPECTED_TITLE), driver);
	}

}

