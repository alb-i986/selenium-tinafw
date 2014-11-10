package me.alb_i986.selenium.tinafw.sample.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import me.alb_i986.selenium.tinafw.ui.BaseWebPage;
import me.alb_i986.selenium.tinafw.ui.WebPage;

/**
 * Models a single box in the {@link SearchResultsPage},
 * representing a single search result,
 * corresponding to an about.me profile.
 *
 */
public class SearchResult extends BaseWebPage {

	private static final By NAME_LOCATOR = By.cssSelector(".thumb_description a");
	private static final By COMPLIMENT_LOCATOR = By.cssSelector(".action.compliment");
	
	private WebElement root;

	public SearchResult(WebElement root, WebDriver driver, WebPage previous) {
		super(driver, previous);
		this.root = root;
	}
	
	public String getName() {
		return root.findElement(NAME_LOCATOR).getText();
	}
	
	public SearchResult complimentWith(String compliment) {
		WebElement complimentBtn = root.findElement(COMPLIMENT_LOCATOR);
		complimentBtn.click();
		new ComplimentDialog(driver, this)
			.compliment(compliment)
		;
		return this;
	}

}
