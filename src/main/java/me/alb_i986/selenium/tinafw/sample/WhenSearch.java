package me.alb_i986.selenium.tinafw.sample;

import org.junit.Assert;

import me.alb_i986.selenium.tinafw.pages.Page;
import me.alb_i986.selenium.tinafw.tasks.MidChainWT;

/**
 * Search for something by using the search box
 * at the top.
 */
public class WhenSearch extends MidChainWT {
	
	private String query;

	@Override
	public Page doTask(Page previousPage) {
		super.doTask(previousPage);
		Assert.assertTrue(previousPage instanceof MyAboutMePage);
		return
		((MyAboutMePage) previousPage)
			.doSearch(query)
		;
	}

	public WhenSearch forQuery(String query) {
		this.query = query;
		return this;
	}

}
