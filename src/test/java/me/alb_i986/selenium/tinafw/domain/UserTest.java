package me.alb_i986.selenium.tinafw.domain;

import static org.junit.Assert.*;
import me.alb_i986.selenium.tinafw.pages.Page;
import me.alb_i986.selenium.tinafw.tasks.WebTask;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class UserTest {

	private User user;

	@Before
	public void before() {
		this.user = new User();
	}
	

	/**
	 * Testing post conditions of {@link User#doTask(WebTask)}:
	 * <ol>
	 * <li>Given a User with no initial page</li>
	 * <li>When user does a task that lands to some not null page</li>
	 * <li>Then {@link User#getCurrentPage()} should return not null</li>
	 * </ol>
	 */
	@Test
	public void doTask_landingToNotNullPage() {
		// open a browser by injecting a WebDriver in the Browser instance
		user.getBrowser().setDriver(new HtmlUnitDriver());
		assertNull(user.getCurrentPage());
		WebTask taskThatReturnsANotNullPage = new WebTask() {
			
			@Override
			public Page doTask(Page initialPage) {
				return new Page() {
					
					@Override
					public String getTitle() {
						return "stub";
					}
					
					@Override
					public String getCurrentUrl() {
						return "stub";
					}
				};
			}
		};
		user.doTask(taskThatReturnsANotNullPage);
		assertNotNull(user.getCurrentPage());
	}

	/**
	 * Testing post conditions of {@link User#doTask(WebTask)}:
	 * <ol>
	 * <li>Given a User with no initial page</li>
	 * <li>When user does a task that lands to some null page</li>
	 * <li>Then {@link User#getCurrentPage()} should return null</li>
	 * </ol>
	 */
	@Test
	public void doTask_landingToNullPage() {
		// open a browser by injecting a WebDriver in the Browser instance
		user.getBrowser().setDriver(new HtmlUnitDriver());
		WebTask taskThatReturnsANullPage = new WebTask() {
			
			@Override
			public Page doTask(Page initialPage) {
				return null;
			}
		};
		user.doTask(taskThatReturnsANullPage);
		assertNull(user.getCurrentPage());
	}
	
	/**
	 * Testing {@link User#User()}:
	 * <ol>
	 * <li>Given a new user with no initial page</li>
	 * <li>Then {@link User#getCurrentPage()} should return null</li>
	 * </ol>
	 */
	@Test
	public void givenNewUserThenCurrentPageShouldBeNull() {
		assertNull(user.getCurrentPage());
	}


	@After
	public void after() {
	}
	
}
