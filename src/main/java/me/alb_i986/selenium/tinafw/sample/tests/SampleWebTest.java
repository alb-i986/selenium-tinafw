package me.alb_i986.selenium.tinafw.sample.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import me.alb_i986.selenium.tinafw.domain.User;
import me.alb_i986.selenium.tinafw.sample.tasks.*;
import me.alb_i986.selenium.tinafw.tasks.*;
import static me.alb_i986.selenium.tinafw.tasks.When.*;
import static me.alb_i986.selenium.tinafw.tasks.Given.*;
import static me.alb_i986.selenium.tinafw.tasks.Then.*;

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
				given(
					new OnMyAboutMePage(user.getBrowser())
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
					new OnMyAboutMePage(user.getBrowser())
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
	
	@After
	public void after() {
		user.closeBrowser();
	}
	
}
