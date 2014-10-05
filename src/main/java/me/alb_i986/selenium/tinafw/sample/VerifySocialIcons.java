package me.alb_i986.selenium.tinafw.sample;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

import me.alb_i986.selenium.tinafw.pages.Page;
import me.alb_i986.selenium.tinafw.tasks.MidChainWT;

/**
 * Verify that each of the given social buttons
 * (passed through {@link #isDisplayed(String)})
 * is displayed.
 */
public class VerifySocialIcons extends MidChainWT {
	
	private List<String> socials = new ArrayList<>();

	@Override
	public Page doTask(Page previousPage) {
		super.doTask(previousPage);
		assertTrue(previousPage instanceof MyAboutMePage);
		for (String social : socials) {
			((MyAboutMePage) previousPage)
				.assertSocialButtonIsDisplayed(social);
		}
		return previousPage;
	}

	public VerifySocialIcons isDisplayed(String social) {
		socials.add(social);
		return this;
	}

}
