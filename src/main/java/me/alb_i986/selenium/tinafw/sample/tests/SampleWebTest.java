package me.alb_i986.selenium.tinafw.sample.tests;

import org.junit.Test;

import me.alb_i986.selenium.tinafw.domain.WebUser;
import me.alb_i986.selenium.tinafw.sample.tasks.*;
import me.alb_i986.selenium.tinafw.tests.JunitWebTest;
import static me.alb_i986.selenium.tinafw.tasks.WebTasks.*;

public class SampleWebTest extends JunitWebTest {

	private WebUser user;
	
	@Override
	public void before() {
		super.before();
		user = new WebUser().withBrowserType(browserType);
		browserManager.registerBrowsers(user.getBrowser());
		htmlReporter.setBrowser(user.getBrowser());
	}

	/**
	 * Given I am on my about.me page,
	 * When I search for "paolo",
	 * Then I can compliment the third guy with love.
	 */
	@Test
	public void testSearch() {
		user.openBrowser();
		user.doTasks(
			given(
				new OnMyAboutMePage()
			),
			when(
				new Search()
					.forQuery("paolo")
			),
			then(
				new CanCompliment()
					.theGuy(2)
					.with("love")
			)
		);
	}

	/**
	 * When I am on my about.me page,
	 * Then verify that bio matches the expected bio,
	 * And that the social icons for github and linkedin are displayed.
	 */
	@Test
	public void testMyPage() {
		user.openBrowser();
		user.doTasks(
			when(
				new OnMyAboutMePage()
			),
			then(
				new VerifyBio()
					.matches(".*Genuinely driven by Passion.*"),
				new VerifySocialIcons()
					.isDisplayed("github")
					.isDisplayed("linkedin")
			)
		);
	}
	
}
