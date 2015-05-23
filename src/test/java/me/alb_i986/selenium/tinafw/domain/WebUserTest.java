package me.alb_i986.selenium.tinafw.domain;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

import me.alb_i986.selenium.tinafw.tasks.BaseWebTask;
import me.alb_i986.selenium.tinafw.tasks.WebTask;
import me.alb_i986.selenium.tinafw.ui.WebDriverFactory;
import me.alb_i986.selenium.tinafw.ui.WebPage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

@RunWith(MockitoJUnitRunner.class)
public class WebUserTest {

	@Mock private WebPage stubPage;
	@Mock private WebDriver mockedDriver;
	@Mock private Browser browser;

	private WebUser user;

	@Before
	public void before() {
		this.user = new WebUser(browser);
	}
	

	/**
	 * Testing post conditions of {@link WebUser#doTask(WebTask)}:
	 * <ol>
	 * <li>Given a User with no initial page</li>
	 * <li>When user does a task that lands to some not null page</li>
	 * <li>Then {@link WebUser#getCurrentPage()} should return not null</li>
	 * </ol>
	 */
	@Test
	public void doTask_landingToNotNullPage() {
		// open a browser by injecting a WebDriver in the Browser instance
		user.getBrowser().setDriver(mockedDriver);
		assertNull(user.getCurrentPage());
		WebTask taskThatReturnsANotNullPage = mock(WebTask.class);
		when(taskThatReturnsANotNullPage.run(null)).thenReturn(stubPage);
		user.doTask(taskThatReturnsANotNullPage);
		assertNotNull(user.getCurrentPage());
		assertSame(stubPage, user.getCurrentPage());
	}

	/**
	 * Testing post conditions of {@link WebUser#doTask(WebTask)}:
	 * <ol>
	 * <li>Given a User with no initial page</li>
	 * <li>When user does a task that lands to some null page</li>
	 * <li>Then {@link WebUser#getCurrentPage()} should return null</li>
	 * </ol>
	 */
	@Test
	public void doTask_landingToNullPage() {
		// open a browser by injecting a WebDriver in the Browser instance
		user.getBrowser().setDriver(mockedDriver);
		WebTask taskThatReturnsANullPage = mock(WebTask.class);
		when(taskThatReturnsANullPage.run(null)).thenReturn(null);
		user.doTask(taskThatReturnsANullPage);
		assertNull(user.getCurrentPage());
	}
	
	/**
	 * Testing the state of {@code currentPage} after instantiating a {@code new}
	 * {@link WebUser#WebUser()}.
	 * <p>
	 * <ol>
	 * <li>Given a new user with no initial page</li>
	 * <li>Then {@link WebUser#getCurrentPage()} should return null</li>
	 * </ol>
	 */
	@Test
	public void givenNewUserThenCurrentPageShouldBeNull() {
		assertNull(user.getCurrentPage());
	}

	/**
	 * Testing the state of {@code currentPage} after {@link WebUser#closeBrowser()}.
	 * <p>
	 * <ol>
	 * <li>Given a user with a not null current page</li>
	 * <li>When {@link WebUser#closeBrowser()}</li>
	 * <li>Then {@link WebUser#getCurrentPage()} should be null</li>
	 * </ol>
	 */
	@Test
	public void whenCloseBrowserThenCurrentPageShouldBeNull() {
		user.setCurrentPage(stubPage);
		assertNotNull(user.getCurrentPage());
		user.closeBrowser();
		assertNull(user.getCurrentPage());
	}

	@Test
	public void equalsToNonUser() {
		Object o = new Object();
		assertFalse(user.equals(o));
	}

	/**
	 * Testing {@link WebUser#equals(Object)}.
	 */
	@Test
	public void equalsToUserWithSameUsername() {
		WebUser userWithSameUsername = new WebUser(browser)
				.withUsername(user.getUsername());
		assertEquals(userWithSameUsername, user);
	}

	/**
	 * Testing {@link WebUser#equals(Object)}.
	 */
	@Test
	public void equalsToUserWithDifferentUsername() {
		WebUser userWithDifferentUsername = new WebUser(browser).withUsername("asdasd");
		// make sure the two usernames are different
		assertNotEquals(user.getUsername(), userWithDifferentUsername.getUsername());

		assertNotEquals(user, userWithDifferentUsername);
	}

	/**
	 * Testing {@link WebUser#equals(Object)}.
	 */
	@Test
	public void equalsToNullObject() {
		Object nullObject = null;
		assertNotEquals(user, nullObject);
	}

	/**
	 * Testing {@link WebUser#equals(Object)}.
	 */
	@Test
	public void equalsToNullUser() {
		WebUser nullUser = null;
		assertNotEquals(user, nullUser);
	}


	@After
	public void after() {
	}
	
}
