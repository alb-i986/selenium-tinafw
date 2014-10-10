package me.alb_i986.selenium.tinafw.sample.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import me.alb_i986.selenium.tinafw.domain.User;
import me.alb_i986.selenium.tinafw.sample.tasks.*;
import me.alb_i986.selenium.tinafw.tasks.*;

public class SampleWebTest {

	private User user;
	
	@Before
	public void before() {
		user = new User();
		user.openBrowser();
	}
	
	@Test
	public void testSearch() {
		user.doTask(
			new BDDWebTask(
				new Given(
					new OnMyAboutMePage(user.getBrowser())
				),
				new When(
					new Search()
						.forQuery("paolo")
				),
				new Then(
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
				new Given(
					new OnMyAboutMePage(user.getBrowser())
				),
				null,
				new Then(
					new VerifyBio()
						.matches(".*Genuinely driven by Passion.*"),
					new VerifySocialIcons()
						.isDisplayed("github")
						.isDisplayed("linkedin")
				)
			)
		);
	}
	
	@After
	public void after() {
		user.closeBrowser();
	}
	
}
