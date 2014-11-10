package me.alb_i986.selenium.tinafw.sample.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import me.alb_i986.selenium.tinafw.ui.BaseWebPage;
import me.alb_i986.selenium.tinafw.ui.PageHelper;
import me.alb_i986.selenium.tinafw.ui.WebPage;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

/**
 * Model the dialog which pops up in the {@link SearchResultsPage}
 * when clicking on the compliment button in any single
 * {@link SearchResult}.
 *
 */
public class ComplimentDialog extends BaseWebPage {

	@FindBy(css = "div.ui-dialog.compliment-modal")
	private WebElement root;
	
	public ComplimentDialog(WebDriver driver, WebPage previous) {
		super(driver, previous);
	}

	public void compliment(String compliment) {
		try {
			By complimentBtnLocator = By.cssSelector("li.compliment." + compliment);
			PageHelper.waitUntil(visibilityOfElementLocated(complimentBtnLocator),
					driver);
			root.findElement(complimentBtnLocator).click();
		} finally {
			PageHelper.hitKeys(driver, Keys.ESCAPE);
		}
	}

}
