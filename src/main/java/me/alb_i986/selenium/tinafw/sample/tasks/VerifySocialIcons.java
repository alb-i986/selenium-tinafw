package me.alb_i986.selenium.tinafw.sample.tasks;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import me.alb_i986.selenium.tinafw.pages.Page;
import me.alb_i986.selenium.tinafw.sample.pages.MyAboutMePage;
import me.alb_i986.selenium.tinafw.tasks.BaseWebTask;

/**
 * Verify that each of the given social buttons
 * (passed through {@link #isDisplayed(String)})
 * is displayed.
 */
public class VerifySocialIcons extends BaseWebTask {
	
	private List<String> socials = new ArrayList<>();

	@Override
	public Page doTask(Page previousPage) {
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
