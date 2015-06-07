package me.alb_i986.selenium.tinafw.domain;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import me.alb_i986.selenium.tinafw.tasks.WebTask;
import me.alb_i986.selenium.tinafw.ui.WebPage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.WebDriver;

@RunWith(MockitoJUnitRunner.class)
public class WebUserTest {

	@Mock private WebPage stubPage;
	@Mock private WebDriver mockedDriver;
	@Mock private WebTask taskStub;
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
		assertNull(user.getCurrentPage());
		WebTask taskThatReturnsANotNullPage = taskStub;
		given(taskThatReturnsANotNullPage.run(any())).willReturn(stubPage);

		// when
		user.doTask(taskThatReturnsANotNullPage);

		assertNotNull(user.getCurrentPage());
		assertSame(stubPage, user.getCurrentPage());
	}

	/**
	 * Testing post conditions of {@link WebUser#doTask(WebTask)}:
	 * <ol>
	 * <li>Given a User with some initial page</li>
	 * <li>When user does a task that lands to some null page</li>
	 * <li>Then {@link WebUser#getCurrentPage()} should return null</li>
	 * </ol>
	 */
	@Test
	public void doTask_landingToNullPage() {
		user.setCurrentPage(stubPage);
		assertNotNull(user.getCurrentPage());
		WebTask taskThatReturnsANullPage = taskStub;
		given(taskThatReturnsANullPage.run(any())).willReturn(null);

		// when
		user.doTask(taskThatReturnsANullPage);

		assertNull(user.getCurrentPage());
	}
	
	/**
	 * Testing the state of {@code currentPage} after instantiating a new
	 * WebUser.
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
	 * <li>Given a user with some initial page</li>
	 * <li>When {@link WebUser#closeBrowser()}</li>
	 * <li>Then {@link WebUser#getCurrentPage()} should be null</li>
	 * </ol>
	 */
	@Test
	public void whenCloseBrowserThenCurrentPageShouldBeNull() {
		user.setCurrentPage(stubPage);
		assertNotNull(user.getCurrentPage());

		// when
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
		WebUser userWithDifferentUsername = new WebUser(browser).withUsername(user.getUsername() + "asd");

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
