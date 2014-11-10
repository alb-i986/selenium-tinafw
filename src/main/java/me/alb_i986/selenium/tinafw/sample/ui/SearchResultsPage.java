package me.alb_i986.selenium.tinafw.sample.ui;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import me.alb_i986.selenium.tinafw.ui.BaseWebPage;
import me.alb_i986.selenium.tinafw.ui.WebPage;
import me.alb_i986.selenium.tinafw.ui.PageHelper;

/**
 * Model the page with the search results.
 *
 */
public class SearchResultsPage extends BaseWebPage {

	@FindBy(css = "#search-results div")
	private WebElement main;
	
	@FindBy(css = "#search-results div.pagethumb2")
	private List<WebElement> searchResults;

	public SearchResultsPage(WebDriver driver, WebPage previous) {
		super(driver, previous);
	}
	
	public SearchResult getSearchResult(int index) {
		return new SearchResult(searchResults.get(index), driver, this);
	}

	@Override
	protected void waitUntilIsLoaded() {
		PageHelper.waitUntil(
			ExpectedConditions.visibilityOf(main),
			driver
		);
	}

}
