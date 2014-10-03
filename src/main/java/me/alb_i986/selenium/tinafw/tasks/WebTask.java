package me.alb_i986.selenium.tinafw.tasks;

import org.apache.log4j.Logger;

import me.alb_i986.selenium.tinafw.domain.User;
import me.alb_i986.selenium.tinafw.pages.Page;

/**
 * A WebTask is supposed to implement a Given, When, or Then step.
 * See also
 * <a href="http://martinfowler.com/bliki/GivenWhenThen.html" target="new">
 * http://martinfowler.com/bliki/GivenWhenThen.html
 * </a>.
 * <p>
 * A WebTask uses page objects to carry on its logic.
 * It is supposed to be data unaware. Test data should be stored
 * in test classes.
 * <p>
 * WebTask makes it easy to chain steps sequentially, in a way that
 * each task can continue the navigation from the page the
 * previous task was visiting.
 * <p>
 * See also {@link User#doTasks(WebTask...)} for the implementation of the chaining.
 */
public abstract class WebTask {
	
	protected static final Logger logger = Logger.getLogger(WebTask.class);

	public abstract Page doTask(Page previousPage);
	public abstract boolean requirePage(Page previousPage);

}
