package me.alb_i986.selenium.tinafw.sample.ui;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import me.alb_i986.selenium.tinafw.ui.BaseWebPage;
import me.alb_i986.selenium.tinafw.ui.WebPage;
import me.alb_i986.selenium.tinafw.ui.PageHelper;

public class SearchResultsPage extends BaseWebPage {

	@FindBy(css = "#search-results div")
	private WebElement main;
	
	@FindBy(css = "#search-results div.pagethumb2")
	private List<WebElement> searchResults;

	public SearchResultsPage(WebDriver driver, WebPage previous) {
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
			By complimentBtnLocator = By.cssSelector("div.ui-dialog.compliment-modal li.compliment." + compliment);
			PageHelper.waitUntil(ExpectedConditions.visibilityOfElementLocated(complimentBtnLocator), driver);
			WebElement complimentBtn = driver.findElement(
				complimentBtnLocator )
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
