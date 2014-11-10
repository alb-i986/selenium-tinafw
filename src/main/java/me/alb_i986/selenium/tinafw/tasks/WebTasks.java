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
	

	private static CompositeWebTask composite(WebTask... components) {
		return new CompositeWebTask(components);
	}

	/**
	 * Static class providing factory methods for creating BDD-style
	 * tasks: givens whens thens.
	 * They are simply {@link CompositeWebTask}'s, but with a meaningful
	 * factory method name, which improves readability of tests.
	 * 
	 * @see <a href="http://martinfowler.com/bliki/GivenWhenThen.html">
	 *      GivenWhenThen, M. Fowler</a>
	 */
	public static class BDD {
		
		/**
		 * A BDD Given step.
		 * 
		 * @param givens
		 * @return a {@link CompositeWebTask} made up of the givens
		 */
		public static CompositeWebTask given(WebTask... givens) {
			return composite(givens);
		}
	
		/**
		 * A BDD When step.
		 * @param whens
		 * @return a {@link CompositeWebTask} made up of the whens
		 */
		public static CompositeWebTask when(WebTask... whens) {
			return composite(whens);
		}
	
		/**
		 * A BDD Then step.
		 * 
		 * @param thens
		 * @return a {@link CompositeWebTask} made up of the thens
		 */
		public static CompositeWebTask then(WebTask... thens) {
			return composite(thens);
		}
	}
	
}
