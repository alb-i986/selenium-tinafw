package me.alb_i986.selenium.tinafw.sample.tasks;

import me.alb_i986.selenium.tinafw.domain.Browser;
import me.alb_i986.selenium.tinafw.pages.Page;
import me.alb_i986.selenium.tinafw.sample.pages.MyAboutMePage;
import me.alb_i986.selenium.tinafw.tasks.TopWebTask;

/**
 * Navigate to my about.me page.
 */
public class OnMyAboutMePage extends TopWebTask {

	public OnMyAboutMePage(Browser browser) {
		super(browser);
	}
	
	@Override
	public Page doTask(Page previousPage) {
		super.doTask(previousPage);
		return browser.browseTo(MyAboutMePage.class);
	}

}
