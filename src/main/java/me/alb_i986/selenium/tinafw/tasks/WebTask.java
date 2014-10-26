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
 * @see CompositeWebTask
 */
public interface WebTask {

	/**
	 * Perform this task as the user {@link #getUser()}.
	 * Precondition: the user must have already been set
	 * (see {@link #setUser(User)}).
	 * 
	 * @param initialPage the page it is displayed before this task starts
	 * @return the last page that is displayed after this task finishes
	 */
	public Page doTask(Page initialPage);
	
	/**
	 * @param user the user this task will run as.
	 * @return this
	 */
	public WebTask setUser(User user);

	/**
	 * @return the user that this task runs as
	 */
	public User getUser();
	
}
