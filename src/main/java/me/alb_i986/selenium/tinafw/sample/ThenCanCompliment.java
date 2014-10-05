package me.alb_i986.selenium.tinafw.sample;

import static org.junit.Assert.*;

import me.alb_i986.selenium.tinafw.pages.Page;
import me.alb_i986.selenium.tinafw.tasks.MidChainWT;

public class ThenCanCompliment extends MidChainWT {

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

	public ThenCanCompliment theGuy(int i) {
		this.searchResultIndex = i;
		return this;
	}

	public ThenCanCompliment with(String compliment) {
		this.compliment = compliment;
		return this;
	}

}
