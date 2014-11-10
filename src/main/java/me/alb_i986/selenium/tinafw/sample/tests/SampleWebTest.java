package me.alb_i986.selenium.tinafw.sample.tests;

import org.junit.Test;

import me.alb_i986.selenium.tinafw.domain.WebUser;
import me.alb_i986.selenium.tinafw.sample.tasks.*;
import me.alb_i986.selenium.tinafw.tests.JunitWebTest;
import static me.alb_i986.selenium.tinafw.tasks.WebTasks.BDD.*;

public class SampleWebTest extends JunitWebTest {

	private WebUser user;
	
	@Override
	public void before() {
		super.before();
		user = new WebUser().withBrowserType(browserType);
		browserManager.registerBrowsers(user.getBrowser());
		htmlReporter.setBrowser(user.getBrowser());
		user.openBrowser();
	}

	@Test
	public void testSearch() {
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
	
	@Test
	public void testMyPage() {
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
