package me.alb_i986.selenium.tinafw.sample.tasks;

import static org.junit.Assert.*;
import me.alb_i986.selenium.tinafw.pages.Page;
import me.alb_i986.selenium.tinafw.sample.pages.SearchResultsPage;
import me.alb_i986.selenium.tinafw.tasks.MidChainWT;

public class CanCompliment extends MidChainWT {

	private int searchResultIndex ;
	private String compliment;


	@Override
	public Page doTask(Page previousPage) {
		super.doTask(previousPage);
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