package me.alb_i986.selenium.tinafw.tasks;

import me.alb_i986.selenium.tinafw.ui.WebPage;

/**
 * Task that does nothing.
 */
public class NullTask extends BaseWebTask {

	/**
	 * @return the very given page
	 */
	@Override
	public WebPage run(WebPage initialPage) {
		return initialPage;
	}

}
