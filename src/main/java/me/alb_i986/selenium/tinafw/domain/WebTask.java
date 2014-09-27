package me.alb_i986.selenium.tinafw.domain;

import me.alb_i986.selenium.tinafw.pages.Page;

/**
 * Each WebTask is supposed to implement a Given, When, or Then step.
 * See also http://martinfowler.com/bliki/GivenWhenThen.html.
 * <p>
 * A WebTask uses page objects to carry on its logic.
 * It is supposed to be data unaware. The data should be stored
 * in the test classes.
 * <p>
 * WebTask makes it easy to chain steps together, in a way that
 * each task can continue the navigation from the page that the
 * previous task was on. 
 * 
 * @see User#doTasks(WebTask...)
 */
public interface WebTask {

	public void doTask(Page previousPage);
	public Page lastVisitedPage();

}
