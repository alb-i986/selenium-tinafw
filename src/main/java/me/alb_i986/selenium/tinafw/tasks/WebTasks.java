package me.alb_i986.selenium.tinafw.tasks;

import org.openqa.selenium.support.ui.ExpectedConditions;

import me.alb_i986.selenium.tinafw.ui.WebPage;

/**
 * Static class providing factory methods for building instances
 * of {@link WebTask}'s.
 * <p>
 * Inspired by WebDriver's {@link ExpectedConditions}.
 *
 */
public class WebTasks {

	protected WebTasks() {
	}

	/**
	 * Sleep for the given number of seconds.
	 * By default, it sleeps for 0 seconds, which means
	 * it is equivalent to {@link NullTask}.
	 * Finally return the very given page (same as {@link NullTask}).
	 */
	public static WebTask sleepFor(long seconds) {
		return new NullTask() {

			@Override
			public WebPage run(WebPage initialPage) {
				try {
					Thread.sleep(seconds * 1000);
				} catch (InterruptedException e) {
					logger.warn("sleep interrupted");
				}
				return super.run(initialPage);
			}
		};
	}
	
}
