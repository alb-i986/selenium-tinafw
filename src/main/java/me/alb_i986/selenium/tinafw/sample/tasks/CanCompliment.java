package me.alb_i986.selenium.tinafw.sample.tasks;

import me.alb_i986.selenium.tinafw.sample.ui.SearchResultsPage;
import me.alb_i986.selenium.tinafw.tasks.BaseWebTask;
import me.alb_i986.selenium.tinafw.ui.WebPage;

public class CanCompliment extends BaseWebTask {

	private int searchResultIndex ;
	private String compliment;


	@Override
	public WebPage run(WebPage previousPage) {
		return
		((SearchResultsPage) previousPage)
			.getSearchResult(searchResultIndex)
			.complimentWith(compliment)
		;
	}

	public CanCompliment theGuy(int index) {
		this.searchResultIndex = index;
		return this;
	}

	public CanCompliment with(String compliment) {
		this.compliment = compliment;
		return this;
	}

}
