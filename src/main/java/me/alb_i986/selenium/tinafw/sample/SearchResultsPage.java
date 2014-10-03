package me.alb_i986.selenium.tinafw.sample;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import me.alb_i986.selenium.tinafw.pages.BasePage;
import me.alb_i986.selenium.tinafw.pages.Page;
import me.alb_i986.selenium.tinafw.pages.PageHelper;

public class SearchResultsPage extends BasePage {

	@FindBy(css = "#search-results div")
	private WebElement main;
	
	@FindBy(css = "#search-results div.pagethumb2")
	private List<WebElement> searchResults;

	public SearchResultsPage(WebDriver driver, Page previous) {
		super(driver, previous);
	}
	
	public String getSearchResultName(int index) {
		return getSearchResult(index)
			.findElement(By.cssSelector(".thumb_description a"))
			.getText();
	}
	
	protected WebElement getSearchResult(int index) {
		return searchResults.get(index);
	}

	public SearchResultsPage assertCanCompliment(int searchResultIndex, String compliment) {
		WebElement searchResult = getSearchResult(searchResultIndex);
		WebElement complimentBtn = searchResult
			.findElement(By.cssSelector(".action.compliment"))
		;
		complimentBtn.click();
		complimentWith(compliment);
		return this;
	}

	private void complimentWith(String compliment) {
		try {
			WebElement complimentBtn = driver.findElement(
				By.cssSelector("div.ui-dialog.compliment-modal li.compliment." + compliment))
			;
			complimentBtn.click();
		} finally {
			PageHelper.hitKeys(driver, Keys.ESCAPE);
		}
	}

	@Override
	protected void waitUntilIsLoaded() {
		PageHelper.waitUntil(
			ExpectedConditions.visibilityOf(main),
			driver
		);
	}

}
