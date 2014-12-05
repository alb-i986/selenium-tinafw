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

	public static final WebTask NULL_TASK = new NullTask();

	protected WebTasks() {
	}

	/**
	 * @return a {@link NullTask} that sleeps for the given number of seconds.
	 */
	public static WebTask sleepFor(int seconds) {
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
