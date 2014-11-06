package me.alb_i986.selenium.tinafw.sample.tasks;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import me.alb_i986.selenium.tinafw.sample.ui.MyAboutMePage;
import me.alb_i986.selenium.tinafw.tasks.BaseWebTask;
import me.alb_i986.selenium.tinafw.ui.WebPage;

/**
 * Verify that each of the given social buttons
 * (passed through {@link #isDisplayed(String)})
 * is displayed.
 */
public class VerifySocialIcons extends BaseWebTask {
	
	private List<String> socials = new ArrayList<>();

	@Override
	public WebPage run(WebPage previousPage) {
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
