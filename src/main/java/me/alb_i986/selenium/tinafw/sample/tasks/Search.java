package me.alb_i986.selenium.tinafw.sample.tasks;

import static org.junit.Assert.*;
import me.alb_i986.selenium.tinafw.pages.Page;
import me.alb_i986.selenium.tinafw.sample.pages.MyAboutMePage;
import me.alb_i986.selenium.tinafw.tasks.BaseWebTask;

/**
 * Search for something by using the search box
 * at the top.
 */
public class Search extends BaseWebTask {
	
	private String query;

	@Override
	public Page run(Page previousPage) {
		assertTrue(previousPage instanceof MyAboutMePage);
		return
		((MyAboutMePage) previousPage)
			.doSearch(query)
		;
	}

	public Search forQuery(String query) {
		this.query = query;
		return this;
	}

}
