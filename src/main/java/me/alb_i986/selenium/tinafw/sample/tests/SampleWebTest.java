package me.alb_i986.selenium.tinafw.sample.tests;

import org.junit.Test;

import me.alb_i986.selenium.tinafw.domain.User;
import me.alb_i986.selenium.tinafw.sample.tasks.*;
import me.alb_i986.selenium.tinafw.tasks.*;
import me.alb_i986.selenium.tinafw.tests.JunitWebTest;
import static me.alb_i986.selenium.tinafw.tasks.BDDWebTask.*;

public class SampleWebTest extends JunitWebTest {

	private User user;
	
	@Override
	public void before() {
		super.before();
		user = new User().withBrowserType(browserType);
		browserManager.registerBrowsers(user.getBrowser());
		htmlReporter.setBrowser(user.getBrowser());
		user.openBrowser();
	}

	@Test
	public void testSearch() {
		user.doTask(
			new BDDWebTask(
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
			)
		);
	}
	
	@Test
	public void testMyPage() {
		user.doTask(
			new BDDWebTask(
				given(
					new OnMyAboutMePage()
				),
				null,
				then(
					new VerifyBio()
						.matches(".*Genuinely driven by Passion.*"),
					new VerifySocialIcons()
						.isDisplayed("github")
						.isDisplayed("linkedin")
				)
			)
		);
	}
	
}
