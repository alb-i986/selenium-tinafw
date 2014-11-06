package me.alb_i986.selenium.tinafw.sample.tasks;

import static org.junit.Assert.*;
import me.alb_i986.selenium.tinafw.sample.ui.SearchResultsPage;
import me.alb_i986.selenium.tinafw.tasks.BaseWebTask;
import me.alb_i986.selenium.tinafw.ui.WebPage;

public class CanCompliment extends BaseWebTask {

	private int searchResultIndex ;
	private String compliment;


	@Override
	public WebPage run(WebPage previousPage) {
		assertTrue(previousPage instanceof SearchResultsPage);
		return
		((SearchResultsPage) previousPage)
			.assertCanCompliment(searchResultIndex, compliment)
		;
	}

	public CanCompliment theGuy(int i) {
		this.searchResultIndex = i;
		return this;
	}

	public CanCompliment with(String compliment) {
		this.compliment = compliment;
		return this;
	}

}
