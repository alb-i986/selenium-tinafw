package me.alb_i986.selenium.tinafw.tasks;

import org.junit.Test;

import me.alb_i986.selenium.tinafw.ui.WebPage;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class WebTasksTest {
	
	/**
	 * Given an initial page,
	 * When sleepFor 3 seconds,
	 * Then the method should return after 3 seconds (let's say between 3 and 4 seconds),
	 * And the returned page is the same object as the one passed.
	 */
	@Test
	public void sleepFor() {
		WebPage initialPage = new WebPage() {
			
			@Override
			public String getTitle() {
				return "title";
			}
			
			@Override
			public String getCurrentUrl() {
				return "current url";
			}
		};
		long startTimeMillis = System.currentTimeMillis();
		WebPage finalPage = WebTasks.sleepFor(3).run(initialPage);
		long endTimeMillis = System.currentTimeMillis();
		assertThat(endTimeMillis - startTimeMillis,
				allOf(
					greaterThanOrEqualTo(3000L),
					lessThanOrEqualTo(4000l)
				)
		);
		assertSame(initialPage, finalPage);
	}
}
