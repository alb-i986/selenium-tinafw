package me.alb_i986.selenium.tinafw.tasks;

import me.alb_i986.selenium.tinafw.tasks.BaseWebTask;
import me.alb_i986.selenium.tinafw.ui.Page;

/**
 * Task that opens the browser.
 */
public class MyBrowserIsOpen extends BaseWebTask {

	/**
	 * Open the browser.
	 * 
	 * @param noPage this param won't be considered: may be null
	 * 
	 * @throws IllegalStateException if user has not been set
	 */
	public Page run(Page noPage) {
		user.openBrowser();
		return null;
	}

}
