package me.alb_i986.selenium.tinafw.tasks;

import org.openqa.selenium.support.ui.ExpectedConditions;

import me.alb_i986.selenium.tinafw.ui.WebPage;

/**
 * Static class providing factory methods for creating instances
 * of common {@link WebTask}'s.
 * Inspired by WebDriver's {@link ExpectedConditions}.
 * <p>
 * It includes factory methods for creating BDD-style tasks, following the 
 * <a href="http://martinfowler.com/bliki/GivenWhenThen.html">
 * Given When Then pattern</a>:
 * {@link #given(WebTask...)}, {@link #when(WebTask...)}, {@link #then(WebTask...)}.
 * They are simply {@link CompositeWebTask}'s, but feature a meaningful
 * name which helps improving the readability of tests.
 *
 */
public class WebTasks {

	/**
	 * Constant that makes {@link #nullTask()} a singleton.
	 * <p>
	 * Though such a task has a state (mainly a user), its state is never used,
	 * therefore it should be safe to make it a singleton.
	 * Tests using it concurrently should be working fine.
	 */
	private static final WebTask NULL_TASK = new BaseWebTask() {
			
			@Override
			public WebPage run(WebPage initialPage) {
				return initialPage;
			}
		};

	protected WebTasks() {
	}

	/**
	 * Task that does nothing, and "finally" returns the very initial page.
	 */
	public static WebTask nullTask() {
		return NULL_TASK;
	}
	
	/**
	 * Task that sleeps for the given number of seconds,
	 * and finally returns the very initial page.
	 */
	public static WebTask sleepFor(int seconds) {
		return new BaseWebTask() {

			@Override
			public WebPage run(WebPage initialPage) {
				try {
					Thread.sleep(seconds * 1000);
				} catch (InterruptedException e) {
					logger.warn("sleep interrupted");
				}
				return initialPage;
			}
		};
	}
	
	/**
	 * @return a {@link CompositeWebTask} made up of the components
	 */
	public static CompositeWebTask composite(WebTask... components) {
		return new CompositeWebTask(components);
	}

	/**
	 * A BDD Given step.
	 * 
	 * @param givens the tasks implementing the steps to satisfy 
	 *        the pre-conditions of a test
	 * @return a {@link CompositeWebTask} made up of the givens
	 */
	public static CompositeWebTask given(WebTask... givens) {
		return composite(givens);
	}

	/**
	 * A BDD When step.
	 * 
	 * @param whens the tasks implementing the <i>trigger</i> for a test
	 * @return a {@link CompositeWebTask} made up of the whens
	 */
	public static CompositeWebTask when(WebTask... whens) {
		return composite(whens);
	}

	/**
	 * A BDD Then step.
	 * 
	 * @param thens the tasks implementing the verification steps for a test
	 * @return a {@link CompositeWebTask} made up of the thens
	 */
	public static CompositeWebTask then(WebTask... thens) {
		return composite(thens);
	}
	
}
