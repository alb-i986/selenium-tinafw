package me.alb_i986.selenium.tinafw.tasks;

import org.junit.Before;
import org.junit.Test;

import me.alb_i986.selenium.tinafw.ui.WebPage;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class WebTasksTest {

	@Mock
	private WebPage stubPage;
	
	/**
	 * Testing {@link WebTasks#nullTask()}.
	 * <p>
	 * Given an initial page,
	 * When {@link WebTasks#nullTask()} is run,
	 * Then the returned page should be the same as the initial page.
	 */
	@Test
	public void nullTask() {
		WebPage finalPage = WebTasks.nullTask().run(stubPage);
		assertSame(stubPage, finalPage);
	}
	
	/**
	 * Testing {@link WebTasks#sleepFor(int)}.
	 * <p>
	 * Given an initial page,
	 * When sleepFor 1 seconds,
	 * Then the method should return after 1 seconds (let's say between 1 and 2 seconds),
	 * And the returned page should be the same as the initial page.
	 */
	@Test
	public void sleepFor() {
		long startTimeMillis = System.currentTimeMillis();
		WebPage finalPage = WebTasks.sleepFor(1).run(stubPage);
		long endTimeMillis = System.currentTimeMillis();
		assertThat(endTimeMillis - startTimeMillis,
				allOf(
					greaterThanOrEqualTo(1000L),
					lessThanOrEqualTo(2000L)
				)
		);
		assertSame(stubPage, finalPage);
	}
}
