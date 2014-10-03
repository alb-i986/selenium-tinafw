package me.alb_i986.selenium.tinafw.sample;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import me.alb_i986.selenium.tinafw.domain.User;

public class SampleWebTest {

	private User user;
	
	@Before
	public void before() {
		user = new User();
		user.openBrowser();
	}
	
	@Test
	public void testSearch() {
		user.doTasks(
			new OnMyAboutMePage(user.getBrowser()),
			new WhenSearch()
				.forQuery("paolo"),
			new ThenCanCompliment()
				.theGuy(2)
				.with("love")
		);
	}

	@Test
	public void testMyPage() {
		user.doTasks(
			new OnMyAboutMePage(user.getBrowser()),
			new VerifyBio()
				.matches(".*Genuinely driven by Passion.*"),
			new VerifySocialIcons()
				.isDisplayed("github")
				.isDisplayed("linkedin")
		);
	}
	
	@After
	public void after() {
		user.closeBrowser();
	}
	
}
