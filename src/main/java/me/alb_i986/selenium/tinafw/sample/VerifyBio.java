package me.alb_i986.selenium.tinafw.sample;

import org.junit.Assert;

import me.alb_i986.selenium.tinafw.pages.Page;
import me.alb_i986.selenium.tinafw.tasks.MidChainWT;

/**
 * Verify that the biography matches the expected text,
 * passed in {@link #matches(String)}.
 */
public class VerifyBio extends MidChainWT {
	
	private String expectedBioRegex;

	@Override
	public Page doTask(Page previousPage) {
		super.doTask(previousPage);
		Assert.assertTrue(previousPage instanceof MyAboutMePage);
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
