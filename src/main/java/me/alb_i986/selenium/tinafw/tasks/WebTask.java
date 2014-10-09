package me.alb_i986.selenium.tinafw.tasks;

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
public interface WebTask {
	
	public Page doTask(Page initialPage);
	
}
