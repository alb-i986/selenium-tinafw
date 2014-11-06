package me.alb_i986.selenium.tinafw.sample.tasks;

import static org.junit.Assert.*;
import me.alb_i986.selenium.tinafw.sample.ui.MyAboutMePage;
import me.alb_i986.selenium.tinafw.tasks.BaseWebTask;
import me.alb_i986.selenium.tinafw.ui.WebPage;

/**
 * Verify that the biography matches the expected text,
 * passed in {@link #matches(String)}.
 */
public class VerifyBio extends BaseWebTask {
	
	private String expectedBioRegex;

	@Override
	public WebPage run(WebPage previousPage) {
		assertTrue(previousPage instanceof MyAboutMePage);
		((MyAboutMePage) previousPage)
			.assertBioMatches(expectedBioRegex)
		;
		return previousPage;
	}

	public VerifyBio matches(String expectedBioRegex) {
		this.expectedBioRegex = expectedBioRegex;
		return this;
	}

}
